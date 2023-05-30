package cn.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.upms.biz.mapper.SysDeptRelationMapper;
import cn.upms.biz.api.entity.SysDept;
import cn.upms.biz.api.entity.SysDeptRelation;
import cn.upms.biz.service.SysDeptRelationService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 */
@Service
@AllArgsConstructor
public class SysDeptRelationServiceImpl extends ServiceImpl<SysDeptRelationMapper, SysDeptRelation> implements SysDeptRelationService {
	private final SysDeptRelationMapper sysDeptRelationMapper;

	/**
	 * 维护部门关系
	 * @param sysDept 部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertDeptRelation(SysDept sysDept) {
		//增加部门关系表
		SysDeptRelation condition = new SysDeptRelation();
		condition.setDescendant(sysDept.getParentId());
		List<SysDeptRelation> relationList = sysDeptRelationMapper
			.selectList(Wrappers.<SysDeptRelation>query().lambda().eq(SysDeptRelation::getDescendant, sysDept.getParentId()))
			.stream().peek(relation -> relation.setDescendant(sysDept.getDeptId())).collect(Collectors.toList());

		if (CollUtil.isNotEmpty(relationList)) {
			this.saveBatch(relationList);
		}

		//自己也要维护到关系表中
		SysDeptRelation own = new SysDeptRelation();
		own.setDescendant(sysDept.getDeptId());
		own.setAncestor(sysDept.getDeptId());
		sysDeptRelationMapper.insert(own);
	}

	/**
	 * 通过ID删除部门关系
	 * @param id 部门ID
	 */
	@Override
	public void deleteAllDeptRealtion(Long id) {
		baseMapper.deleteDeptRelationsById(id);
	}

	/**
	 * 更新部门关系
	 * @param relation 关联关系
	 */
	@Override
	public void updateDeptRealtion(SysDeptRelation relation) {
		baseMapper.updateDeptRelations(relation);
	}

}
