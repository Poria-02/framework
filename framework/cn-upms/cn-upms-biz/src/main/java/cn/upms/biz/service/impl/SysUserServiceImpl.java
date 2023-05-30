package cn.upms.biz.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.upms.biz.api.dto.UserPwdUpdateDTO;
import cn.shanxincd.plat.upms.api.entity.*;
import cn.upms.biz.mapper.SysUserMapper;
import cn.shanxincd.plat.upms.service.*;
import cn.shanxincd.plat.common.core.exception.ServiceException;
import cn.shanxincd.plat.common.security.util.SecurityUtils;
import cn.upms.biz.api.dto.UserDTO;
import cn.upms.biz.api.dto.UserInfo;
import cn.upms.biz.api.entity.*;
import cn.upms.biz.api.vo.MenuVO;
import cn.upms.biz.api.vo.UserVO;
import cn.upms.biz.service.*;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.shanxincd.plat.common.core.constant.CacheConstants;
import cn.shanxincd.plat.common.core.constant.CommonConstants;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	private final SysMenuService sysMenuService;
	private final SysRoleService sysRoleService;
	private final SysDeptService sysDeptService;
	private final SysUserRoleService sysUserRoleService;
	private final SysDeptRelationService sysDeptRelationService;

	/**
	 * 保存用户信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		sysUser.setLastPwdUpdateTime(new Date());
		baseMapper.insert(sysUser);
		List<SysUserRole> userRoleList = userDto.getRole().stream().map(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRole.setRoleId(roleId);
			return userRole;
		}).collect(Collectors.toList());

		return sysUserRoleService.saveBatch(userRoleList);
	}

	/**
	 * 绑定网易云信Token
	 * @param userId  用户ID
	 * @param yxToken 云信Token
	 */
	@Override
	public Boolean bindYXToken(Integer userId, String yxToken) {
		SysUser sysUser = getById(userId);
		if (sysUser == null){
			return false;
		}

		//修改用户
		sysUser.setYxToken(yxToken);
		return this.updateById(sysUser);
	}

	/**
	 * 通过查用户的全部信息
	 * @param sysUser 用户
	 */
	@Override
	public UserInfo findUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);

		//设置角色列表  （ID）
		List<Long> roleIds = sysRoleService.findRolesByUserId(sysUser.getUserId())
				.stream()
				.map(SysRole::getRoleId)
				.collect(Collectors.toList());

		userInfo.setRoles(ArrayUtil.toArray(roleIds, Long.class));

		//设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		roleIds.forEach(roleId -> {
			List<String> permissionList = sysMenuService.findMenuByRoleId(roleId)
					.stream()
					.filter(menuVo -> StrUtil.isNotEmpty(menuVo.getPermission()))
					.map(MenuVO::getPermission)
					.collect(Collectors.toList());

			permissions.addAll(permissionList);
		});

		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 */
	@Override
	public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO, new DataScope());
	}

	/**
	 * 通过ID查询用户信息
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO selectUserVoById(Integer id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 * @param sysUser 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
	public Boolean deleteUserById(SysUser sysUser) {
		sysUserRoleService.deleteByUserId(sysUser.getUserId());
		this.removeById(sysUser.getUserId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public R<Boolean> updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getPassword())
				&& StrUtil.isNotBlank(userDto.getNewpassword1())) {
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
				sysUser.setLastPwdUpdateTime(new Date());
			} else {
				log.info("原密码错误，修改密码失败:{}", userDto.getUsername());
				return R.failed("原密码错误，修改失败");
			}
		}

		sysUser.setPhone(userDto.getPhone());
		sysUser.setUserId(userVO.getUserId());
		sysUser.setAvatar(userDto.getAvatar());
		return R.ok(this.updateById(sysUser));
	}


	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public R<Boolean> updateUserPwd(UserPwdUpdateDTO userDto) {
		UserVO existUser = baseMapper.getUserVoByUsername(userDto.getUsername());
		if(existUser == null){
			return R.failed("未查询到用户信息");
		}

		if(StrUtil.isBlank(userDto.getPassword()) ||  StrUtil.isBlank(userDto.getNewpassword1())){
			return R.failed("参数错误");
		}

		if(!ENCODER.matches(userDto.getPassword(), existUser.getPassword())){
			return R.failed("原密码错误，修改失败");
		}

		if(ENCODER.matches(userDto.getNewpassword1(), existUser.getPassword())){
			return R.failed("新密码不能与原密码相同");
		}

		return R.ok(this.update(new LambdaUpdateWrapper<SysUser>()
				.set(SysUser::getPassword,ENCODER.encode(userDto.getNewpassword1()))
				.set(SysUser::getLastPwdUpdateTime,new Date())
				.eq(SysUser::getUserId,existUser.getUserId())
		));
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());

		if (StrUtil.isNotBlank(userDto.getPassword())) {
			if(!Pattern.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,20}$"
					,userDto.getPassword())){
				throw new ServiceException("密码必须包含大小写字母、数字、特殊符号，且至少8位");
			}

			sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
			sysUser.setLastPwdUpdateTime(new Date());
		}
		this.updateById(sysUser);
		sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userDto.getUserId()));
		userDto.getRole().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});

		return Boolean.TRUE;
	}

	/**
	 * 查询上级部门的用户信息
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsers(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
		SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
		if (sysDept == null) {
			return null;
		}

		Long parentId = sysDept.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getDeptId, parentId));
	}

	/**
	 * 获取当前用户的子部门信息
	 * @return 子部门列表
	 */
	private List<Long> getChildDepts() {
		Long deptId = SecurityUtils.getUser().iDeptId();
		//获取当前部门的子部门
		return sysDeptRelationService
				.list(Wrappers.<SysDeptRelation>query().lambda().eq(SysDeptRelation::getAncestor, deptId))
				.stream()
				.map(SysDeptRelation::getDescendant)
				.collect(Collectors.toList());
	}
}
