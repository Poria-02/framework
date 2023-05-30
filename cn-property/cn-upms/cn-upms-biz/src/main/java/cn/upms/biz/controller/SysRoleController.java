package cn.upms.biz.controller;

import cn.common.core.utils.R;
import cn.common.security.annotation.Inner;
import cn.upms.api.entity.SysRole;
import cn.upms.api.vo.RoleVO;
import cn.upms.biz.service.SysRoleMenuService;
import cn.upms.biz.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Tag(name = "role", description = "角色管理模块")
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysRoleService.getById(id));
    }

    /**
     * 通过角色code查询角色信息
     *
     * @param code 角色code
     * @return 角色信息
     */
    @Inner
    @GetMapping("/code/{code}")
    public R getById(@PathVariable String code) {
        return R.ok(sysRoleService.getOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleCode, code)));
    }

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     * @return success/false
     */
    @SysLog("添加角色")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_role_add')")
    public R save(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.save(sysRole));
    }

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     * @return success/false
     */
    @SysLog("修改角色")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_role_edit')")
    public R update(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.updateById(sysRole));
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return success/false
     */
    @SysLog("删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_role_del')")
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysRoleService.removeRoleById(id));
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    public R listRoles() {
        return R.ok(sysRoleService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R getRolePage(Page page) {
        return R.ok(sysRoleService.page(page, Wrappers.emptyWrapper()));
    }

    /**
     * 更新角色菜单
     *
     * @param roleVo 角色对象
     * @return success、false
     */
    @SysLog("更新角色菜单")
    @PutMapping("/menu")
    @PreAuthorize("@pms.hasPermission('sys_role_perm')")
    public R saveRoleMenus(@RequestBody RoleVO roleVo) {
        SysRole sysRole = sysRoleService.getById(roleVo.getRoleId());
        return R.ok(sysRoleMenuService.saveRoleMenus(sysRole.getRoleCode(), roleVo.getRoleId(), roleVo.getMenuIds()));
    }

    /**
     * 通过角色ID 查询角色列表
     *
     * @param roleIdList 角色ID
     * @return List<SysRole>
     */
    @PostMapping("/getRoleList")
    public R getRoleList(@RequestBody List<String> roleIdList) {
        return R.ok(sysRoleService.listByIds(roleIdList));
    }
}
