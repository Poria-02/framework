package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysDept;
import cn.upms.biz.api.entity.SysDeptRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 */
public interface SysDeptRelationService extends IService<SysDeptRelation> {
	/**
	 * 新建部门关系
	 * @param sysDept 部门
	 */
	void insertDeptRelation(SysDept sysDept);

	/**
	 * 通过ID删除部门关系
	 * @param id	部门ID
	 */
	void deleteAllDeptRealtion(Long id);

	/**
	 * 更新部门关系
	 * @param relation 关联关系
	 */
	void updateDeptRealtion(SysDeptRelation relation);
}
