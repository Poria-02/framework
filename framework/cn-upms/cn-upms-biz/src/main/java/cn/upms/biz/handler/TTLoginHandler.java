package cn.upms.biz.handler;

import cn.upms.biz.api.dto.UserInfo;
import cn.upms.biz.api.entity.SysUser;
import cn.upms.biz.service.SysUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** 天桃登录 */
@Slf4j
@Component("TT")
@AllArgsConstructor
public class TTLoginHandler extends AbstractLoginHandler {
	private final SysUserService sysUserService;

	/**
	 * 天桃登录，传进来的是天桃的Token
	 */
	@Override
	public String identify(String mobile) {
		return mobile;
	}

	/**
	 * 通过 mobile 获取用户信息
	 */
	@Override
	public UserInfo info(String identify) {
		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, identify));
		if (user == null) {
			log.info("天桃手机号未注册:{}", identify);
			return null;
		}

		return sysUserService.findUserInfo(user);
	}
}
