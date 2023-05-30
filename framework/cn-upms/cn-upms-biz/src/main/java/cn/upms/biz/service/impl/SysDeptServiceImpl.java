package cn.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.upms.biz.mapper.SysDeptMapper;
import cn.upms.biz.service.SysDeptRelationService;
import cn.upms.biz.api.dto.DeptTree;
import cn.upms.biz.api.entity.SysDept;
import cn.upms.biz.api.entity.SysDeptRelation;
import cn.upms.biz.api.vo.TreeUtil;
import cn.upms.biz.service.SysDeptService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.shanxincd.plat.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现类
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
	private final SysDeptRelationService sysDeptRelationService;

	/**
	 * 添加信息部门
	 * @param dept 部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysDept saveDept(SysDept dept) {
		SysDept sysDept = new SysDept();
		BeanUtils.copyProperties(dept, sysDept);
		this.save(sysDept);
		sysDeptRelationService.insertDeptRelation(sysDept);
		return sysDept;
	}

	/**
	 * 删除部门
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeDeptById(Long id) {
		//级联删除部门
		List<Long> idList = sysDeptRelationService
			.list(Wrappers.<SysDeptRelation>query().lambda().eq(SysDeptRelation::getAncestor, id))
			.stream()
			.map(SysDeptRelation::getDescendant)
			.collect(Collectors.toList());

		if (CollUtil.isNotEmpty(idList)) {
			this.removeByIds(idList);
		}

		//删除部门级联关系
		sysDeptRelationService.deleteAllDeptRealtion(id);
		return Boolean.TRUE;
	}

	/**
	 * 更新部门
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateDeptById(SysDept sysDept) {
		//更新部门状态
		this.updateById(sysDept);

		//更新部门关系
		SysDeptRelation relation = new SysDeptRelation();
		relation.setAncestor(sysDept.getParentId());
		relation.setDescendant(sysDept.getDeptId());
		sysDeptRelationService.updateDeptRealtion(relation);
		return Boolean.TRUE;
	}

	/**
	 * 查询全部部门树
	 * @return 树
	 */
	@Override
	public List<DeptTree> selectTree() {
		return getDeptTree(baseMapper.listDepts(new DataScope()));
	}

	/**
	 * 查询部门树菜单
	 *
	 * @param departId
	 * @return
	 */
	@Override
	public List<DeptTree> selectTree(Long departId) {

		DataScope dataScope = new DataScope();
		dataScope.getDeptList().add(departId);
		List<SysDeptRelation> list = sysDeptRelationService.list(Wrappers.<SysDeptRelation>lambdaQuery()
				.eq(SysDeptRelation::getAncestor, departId));
		if(CollectionUtil.isNotEmpty(list)){
			dataScope.getDeptList().addAll(list.stream().map(SysDeptRelation::getDescendant).collect(Collectors.toList()));
		}
		return getDeptTree(baseMapper.listDepts(dataScope));
	}

	/**
	 * 构建部门树
	 * @param depts 部门
	 */
	private List<DeptTree> getDeptTree(List<SysDept> depts) {
		List<DeptTree> treeList = depts.stream()
			.filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
			.sorted(Comparator.comparingInt(SysDept::getSort))
			.map(dept -> {
				DeptTree node = new DeptTree();
				node.setId(dept.getDeptId());
				node.setParentId(dept.getParentId());
				node.setName(dept.getName());
				return node;
			}).collect(Collectors.toList());
		return TreeUtil.build(treeList, depts.stream().min(Comparator.comparingLong(SysDept::getDeptId)).get().getParentId());
	}
}
