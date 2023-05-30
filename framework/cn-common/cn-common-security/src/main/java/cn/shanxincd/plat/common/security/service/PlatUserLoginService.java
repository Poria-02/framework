package cn.shanxincd.plat.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zhangchunlei
 * @date 2021年06月07日 4:43 下午
 */
public interface PlatUserLoginService {

	String SERVICE_ID = "platUserLoginServiceImplOf";

	UserDetails loadUser(PlatAuthenticationToken token,boolean checkVerifyCode);

}
