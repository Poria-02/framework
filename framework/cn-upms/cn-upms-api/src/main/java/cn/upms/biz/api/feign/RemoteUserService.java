package cn.upms.biz.api.feign;

import cn.upms.biz.api.entity.SysUser;
import cn.upms.biz.api.dto.UserInfo;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.constant.ServiceNameConstants;
import cn.shanxincd.plat.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "remoteUserService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteUserService {
	/**
	 * 通过用户名查询用户、角色信息
	 * @param username 用户名
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/user/info/{username}")
	R<UserInfo> info(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过用户名查询用户、角色信息
	 * @param mobile   手机号码
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/user/mobile/{mobile}")
	R<SysUser> mobile(@PathVariable("mobile") String mobile, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过社交账号或手机号查询用户、角色信息
	 * @param inStr appid@code
	 * @param from  调用标志
	 * @return R
	 */
	@GetMapping("/social/info/{inStr}")
	R<UserInfo> social(@PathVariable("inStr") String inStr, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 查询上级部门的用户信息
	 * @param username 用户名
	 * @return R
	 */
	@GetMapping("/user/ancestor/{username}")
	R<List<SysUser>> ancestorUsers(@PathVariable("username") String username);

	/**
	 * 绑定网易云信Token
	 * @param userId 用户ID
	 * @param token	 云信Token
	 */
	@GetMapping("/user/yxtoken/{userId}/{token}")
	void bindYXToken(@PathVariable("userId") Integer userId, @PathVariable("token") String token, @RequestHeader(SecurityConstants.FROM) String from);
}
