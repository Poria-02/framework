package cn.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.upms.biz.mapper.SysMenuMapper;
import cn.upms.biz.mapper.SysRoleMenuMapper;
import cn.upms.biz.service.SysMenuService;
import cn.shanxincd.plat.common.core.constant.CacheConstants;
import cn.shanxincd.plat.common.core.constant.CommonConstants;
import cn.shanxincd.plat.common.core.constant.enums.MenuTypeEnum;
import cn.shanxincd.plat.common.core.util.R;
import cn.upms.biz.api.dto.MenuTree;
import cn.upms.biz.api.entity.SysMenu;
import cn.upms.biz.api.entity.SysRoleMenu;
import cn.upms.biz.api.vo.MenuVO;
import cn.upms.biz.api.vo.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 菜单权限表 服务实现类
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	private final SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	@Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result.isEmpty()")
	public List<MenuVO> findMenuByRoleId(Long roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public R removeMenuById(Integer id) {
		//查询父节点为当前节点的节点
		List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id));
		if (CollUtil.isNotEmpty(menuList)) {
			return R.failed("菜单含有下级不能删除");
		}

		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getMenuId, id));
		//删除当前菜单及其子菜单
		return R.ok(this.removeById(id));
	}

	@Override
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public Boolean updateMenuById(SysMenu sysMenu) {
		return this.updateById(sysMenu);
	}

	/**
	 * 构建树查询
	 * 1. 不是懒加载情况，查询全部
	 * 2. 是懒加载，根据parentId 查询
	 * 2.1 父节点为空，则查询ID -1
	 *
	 * @param lazy     是否是懒加载
	 * @param parentId 父节点ID
	 * @return
	 */
	@Override
	public List<MenuTree> treeMenu(boolean lazy, Integer parentId) {
		if (!lazy) {
			return TreeUtil.buildTree(baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery()
				.orderByAsc(SysMenu::getSort)), CommonConstants.MENU_TREE_ROOT_ID);
		}

		Integer parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;
		return TreeUtil.buildTree(baseMapper
			.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parent)
				.orderByAsc(SysMenu::getSort)), parent);
	}

	/**
	 * 查询菜单
	 * @param all      全部菜单
	 * @param type     类型
	 * @param parentId 父节点ID
	 */
	@Override
	public List<MenuTree> filterMenu(Set<MenuVO> all, String type, Integer parentId) {
		List<MenuTree> menuTreeList = all.stream()
			.filter(menuTypePredicate(type))
			.map(MenuTree::new)
			.sorted(Comparator.comparingInt(MenuTree::getSort))
			.collect(Collectors.toList());

		Integer parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;
		return TreeUtil.build(menuTreeList, parent);
	}

	/**
	 * menu 类型断言
	 * @param type 类型
	 * @return Predicate
	 */
	private Predicate<MenuVO> menuTypePredicate(String type) {
		return vo -> {
			if (MenuTypeEnum.TOP_MENU.getDescription().equals(type)) {
				return MenuTypeEnum.TOP_MENU.getType().equals(vo.getType());
			}

			//其他查询 左侧 + 顶部
			return !MenuTypeEnum.BUTTON.getType().equals(vo.getType());
		};
	}
}
