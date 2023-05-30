package cn.upms.biz.service.impl;

import cn.upms.biz.mapper.SysRoleMapper;
import cn.upms.biz.mapper.SysRoleMenuMapper;
import cn.upms.biz.api.entity.SysRole;
import cn.upms.biz.api.entity.SysRoleMenu;
import cn.upms.biz.service.SysRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysRoleMenuMapper sysRoleMenuMapper;

	/**
	 * 通过用户ID，查询角色信息
	 * @param userId 用户ID
	 * @return	角色列表
	 */
	@Override
	public List<SysRole> findRolesByUserId(Long userId) {
		return baseMapper.listRolesByUserId(userId);
	}

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 * @param id 角色ID
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeRoleById(Integer id) {
		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>update().lambda().eq(SysRoleMenu::getRoleId, id));
		return this.removeById(id);
	}
}
