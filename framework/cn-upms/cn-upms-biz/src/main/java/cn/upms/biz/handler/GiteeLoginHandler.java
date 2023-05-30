package cn.upms.biz.handler;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.upms.biz.mapper.SysSocialDetailsMapper;
import cn.upms.biz.api.dto.UserInfo;
import cn.upms.biz.api.entity.SysSocialDetails;
import cn.upms.biz.api.entity.SysUser;
import cn.upms.biz.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/** 码云登录 */
@Slf4j
@Component("GITEE")
@AllArgsConstructor
public class GiteeLoginHandler extends AbstractLoginHandler {

	private final SysUserService sysUserService;
	private final SysSocialDetailsMapper sysSocialDetailsMapper;

	/**
	 * 码云登录传入code
	 * 通过code 调用qq 获取唯一标识
	 */
	@Override
	public String identify(String code) {
		SysSocialDetails condition = new SysSocialDetails();
		condition.setType(LoginTypeEnum.GITEE.getType());
		SysSocialDetails socialDetails = sysSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));

		String url = String.format(SecurityConstants.GITEE_AUTHORIZATION_CODE_URL, code, socialDetails.getAppId(), URLUtil.encode(socialDetails.getRedirectUrl()), socialDetails.getAppSecret());
		String result = HttpUtil.post(url, new HashMap<>(0));
		log.debug("码云响应报文:{}", result);

		String accessToken = JSONUtil.parseObj(result).getStr("access_token");
		String userUrl = String.format(SecurityConstants.GITEE_USER_INFO_URL, accessToken);
		String resp = HttpUtil.get(userUrl);
		log.debug("码云获取个人信息返回报文{}", resp);
		JSONObject userInfo = JSONUtil.parseObj(resp);

		//码云唯一标识
		return userInfo.getStr("login");
	}

	/**
	 * identify 获取用户信息
	 */
	@Override
	public UserInfo info(String identify) {
		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getGiteeLogin, identify));
		if (user == null) {
			log.info("码云未绑定:{}", identify);
			return null;
		}

		return sysUserService.findUserInfo(user);
	}
}
