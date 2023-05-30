package cn.upms.biz.mapper;

import cn.upms.biz.api.entity.SysMenu;
import cn.upms.biz.api.vo.MenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表 Mapper 接口
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 */
	List<MenuVO> listMenusByRoleId(Long roleId);

	/**
	 * 通过角色ID查询权限
	 * @param roleIds Ids
	 */
	List<String> listPermissionsByRoleIds(String roleIds);
}
