//package cn.upms.biz.handler;
//
//import cn.common.core.constant.SecurityConstants;
//import cn.hutool.http.HttpUtil;
//import cn.hutool.json.JSONUtil;
//import cn.shanxincd.plat.common.core.constant.SecurityConstants;
//import cn.shanxincd.plat.common.core.constant.enums.LoginTypeEnum;
//import cn.shanxincd.plat.upms.api.dto.UserInfo;
//import cn.shanxincd.plat.upms.api.entity.SysSocialDetails;
//import cn.shanxincd.plat.upms.api.entity.SysUser;
//import cn.shanxincd.plat.upms.mapper.SysSocialDetailsMapper;
//import cn.shanxincd.plat.upms.service.SysUserService;
//import cn.upms.api.dto.UserInfo;
//import cn.upms.api.entity.SysSocialDetails;
//import cn.upms.api.entity.SysUser;
//import cn.upms.biz.mapper.SysSocialDetailsMapper;
//import cn.upms.biz.service.SysUserService;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * 微信小程序
// */
//@Slf4j
//@Component("MINI")
//@AllArgsConstructor
//public class MiniAppLoginHandler extends AbstractLoginHandler {
//	private final SysUserService sysUserService;
//	private final SysSocialDetailsMapper sysSocialDetailsMapper;
//
//	/**
//	 * 小程序登录传入code
//	 * 通过code 调用qq 获取唯一标识
//	 */
//	@Override
//	public String identify(String code) {
//		SysSocialDetails condition = new SysSocialDetails();
//		condition.setType(LoginTypeEnum.MINI_APP.getType());
//		SysSocialDetails socialDetails = sysSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));
//
//		String url = String.format(SecurityConstants.MINI_APP_AUTHORIZATION_CODE_URL, socialDetails.getAppId(), socialDetails.getAppSecret(), code);
//		String result = HttpUtil.get(url);
//		log.debug("微信小程序响应报文:{}", result);
//
//		Object obj = JSONUtil.parseObj(result).get("openid");
//		return obj.toString();
//	}
//
//	/**
//	 * openId 获取用户信息
//	 * @return
//	 */
//	@Override
//	public UserInfo info(String openId) {
//		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getMiniOpenid, openId));
//		if (user == null) {
//			log.info("微信小程序未绑定:{}", openId);
//			return null;
//		}
//
//		return sysUserService.findUserInfo(user);
//	}
//}
