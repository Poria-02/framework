package cn.upms.biz.controller;

import cn.upms.biz.api.dto.UserPwdUpdateDTO;
import cn.shanxincd.plat.common.log.annotation.SysLog;
import cn.shanxincd.plat.common.security.annotation.Inner;
import cn.upms.biz.api.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.upms.biz.api.dto.UserDTO;
import cn.upms.biz.service.SysUserService;
import cn.shanxincd.plat.common.core.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理模块")
public class SysUserController {
	private final SysUserService userService;

	/**
	 * 获取指定用户全部信息
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{username}")
	public R info(@PathVariable String username) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return R.failed(null, String.format("用户信息为空 %s", username));
		}

		//查询全部信息
		return R.ok(userService.findUserInfo(user));
	}

	/**
	 * 获取指定用户全部信息 根据手机号码
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/mobile/{mobile}")
	public R mobile(@PathVariable String mobile) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, mobile));
		if (user == null) {
			return R.failed(null, String.format("用户信息为空 %s", mobile));
		}

		//返回基础信息
		return R.ok(user);
	}

	/**
	 * 通过ID查询用户信息
	 * @param id ID
	 * @return 用户信息
	 */
	@GetMapping("/{id}")
	public R user(@PathVariable Integer id) {
		return R.ok(userService.selectUserVoById(id));
	}

	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 */
	@GetMapping("/details/{username}")
	public R user(@PathVariable String username) {
		SysUser condition = new SysUser();
		condition.setUsername(username);
		return R.ok(userService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 删除用户信息
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除用户信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	@Operation(summary = "删除用户", description = "根据ID删除用户")
	public R userDel(@PathVariable Integer id) {
		SysUser sysUser = userService.getById(id);
		return R.ok(userService.deleteUserById(sysUser));
	}

	/**
	 * 添加用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@SysLog("添加用户")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	public R user(@RequestBody @Validated UserDTO userDto) {
		return R.ok(userService.saveUser(userDto));
	}

	/**
	 * 更新用户信息
	 * @param userDto 用户信息
	 * @return R
	 */
	@SysLog("更新用户信息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public R updateUser(@Valid @RequestBody UserDTO userDto) {
		return R.ok(userService.updateUser(userDto));
	}

	/**
	 * 分页查询用户
	 * @param page    参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@GetMapping("/page")
	public R getUserPage(Page page, UserDTO userDTO) {
		return R.ok(userService.getUsersWithRolePage(page, userDTO));
	}

	/**
	 * 修改个人信息
	 * @param userDto userDto
	 * @return success/false
	 */
	@SysLog("修改个人信息")
	@PutMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		return userService.updateUserInfo(userDto);
	}

	@Inner(false)
	@SysLog("修改个人信息")
	@PutMapping("/pwd/update")
	public R updateUserPwd(@Valid @RequestBody UserPwdUpdateDTO userDto) {
		return userService.updateUserPwd(userDto);
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@GetMapping("/ancestor/{username}")
	public R listAncestorUsers(@PathVariable String username) {
		return R.ok(userService.listAncestorUsers(username));
	}

	/**
	 * 绑定网易云信Token
	 * @param userId 用户ID
	 * @param token  网易云信Token
	 */
	@Inner
	@GetMapping("/yxtoken/{userId}/{token}")
	public R bindYXToken(@PathVariable Integer userId, @PathVariable String token) {
		return R.ok(userService.bindYXToken(userId, token));
	}
}
