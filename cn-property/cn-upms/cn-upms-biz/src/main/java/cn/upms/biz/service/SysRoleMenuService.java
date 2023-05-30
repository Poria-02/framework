package cn.upms.biz.service;

import cn.upms.api.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色菜单表 服务类
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 更新角色菜单
     *
     * @param role    角色名
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return boolean
     */
    Boolean saveRoleMenus(String role, Integer roleId, String menuIds);
}
