package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 服务类
 */
public interface SysRoleService extends IService<SysRole> {
	/**
	 * 通过用户ID，查询角色信息
	 */
	List<SysRole> findRolesByUserId(Long userId);

	/**
	 * 通过角色ID，删除角色
	 */
	Boolean removeRoleById(Integer id);
}
