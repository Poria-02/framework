package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户角色表 服务类
 */
public interface SysUserRoleService extends IService<SysUserRole> {
	/**
	 * 根据用户Id删除该用户的角色关系
	 */
	Boolean deleteByUserId(Long userId);
}
