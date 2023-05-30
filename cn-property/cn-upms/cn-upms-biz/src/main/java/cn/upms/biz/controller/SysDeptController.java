package cn.upms.biz.controller;

import cn.common.core.utils.R;
import cn.common.security.annotation.Inner;
import cn.upms.api.entity.SysDept;
import cn.upms.api.entity.SysDeptRelation;
import cn.upms.biz.service.SysDeptRelationService;
import cn.upms.biz.service.SysDeptService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 部门管理 前端控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Tag(name = "dept", description = "部门管理模块")
public class SysDeptController {
    private final SysDeptService sysDeptService;
    private final SysDeptRelationService relationService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @Inner(false)
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysDeptService.getById(id));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R getTree() {
        return R.ok(sysDeptService.selectTree());
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @Inner(value = false)
    @GetMapping(value = "/tree/{departId}")
    public R getTree(@PathVariable("departId") Long departId) {
        return R.ok(sysDeptService.selectTree(departId));
    }

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @SysLog("添加部门")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_dept_add')")
    public R save(@Valid @RequestBody SysDept sysDept) {
        return R.ok(sysDeptService.saveDept(sysDept));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @SysLog("删除部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_dept_del')")
    public R removeById(@PathVariable Long id) {
        return R.ok(sysDeptService.removeDeptById(id));
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @SysLog("编辑部门")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_dept_edit')")
    public R update(@Valid @RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        return R.ok(sysDeptService.updateDeptById(sysDept));
    }

    /**
     * 查收子级列表
     *
     * @return 返回子级
     */
    @GetMapping(value = "/getDescendantList/{deptId}")
    public R getDescendantList(@PathVariable Integer deptId) {
        return R.ok(relationService.list(Wrappers.<SysDeptRelation>lambdaQuery().eq(SysDeptRelation::getAncestor, deptId)));
    }
}
