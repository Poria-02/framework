package cn.upms.biz.service;

import cn.shanxincd.plat.common.core.util.R;
import cn.upms.biz.api.dto.MenuTree;
import cn.upms.biz.api.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.upms.biz.api.vo.MenuVO;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表 服务类
 */
public interface SysMenuService extends IService<SysMenu> {
	/**
	 * 通过角色编号查询URL 权限
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<MenuVO> findMenuByRoleId(Long roleId);

	/**
	 * 级联删除菜单
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	R removeMenuById(Integer id);

	/**
	 * 更新菜单信息
	 * @param sysMenu 菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(SysMenu sysMenu);

	/**
	 * 构建树
	 * @param lazy     是否是懒加载
	 * @param parentId 父节点ID
	 */
	List<MenuTree> treeMenu(boolean lazy, Integer parentId);

	/**
	 * 查询菜单
	 */
	List<MenuTree> filterMenu(Set<MenuVO> voSet, String type, Integer parentId);
}