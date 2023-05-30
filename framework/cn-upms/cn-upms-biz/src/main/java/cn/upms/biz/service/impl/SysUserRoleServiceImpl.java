package cn.upms.biz.service.impl;

import cn.upms.biz.mapper.SysUserRoleMapper;
import cn.upms.biz.api.entity.SysUserRole;
import cn.upms.biz.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户角色表 服务实现类
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 * @param userId 用户ID
	 * @return boolean
	 */
	@Override
	public Boolean deleteByUserId(Long userId) {
		return baseMapper.deleteByUserId(userId);
	}
}
