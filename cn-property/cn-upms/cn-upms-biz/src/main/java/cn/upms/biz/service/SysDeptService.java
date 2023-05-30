package cn.upms.biz.service;

import cn.upms.api.dto.DeptTree;
import cn.upms.api.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 部门管理 服务类
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 查询部门树菜单
     *
     * @return 树
     */
    List<DeptTree> selectTree();

    /**
     * 查询部门树菜单
     *
     * @param departId
     * @return
     */
    List<DeptTree> selectTree(Long departId);

    /**
     * 添加信息部门
     */
    SysDept saveDept(SysDept sysDept);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean removeDeptById(Long id);

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);
}