package cn.shanxincd.plat.common.security.service;

import cn.shanxincd.plat.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangchunlei
 * @date 2021年06月05日 10:13 上午
 */
@Component
public class PlatUserServiceContext {

	@Autowired(required = false)
	private Map<String,PlatUserLoginService> userLoginServiceContext;

	public UserDetails loadUser(Authentication authentication){

		PlatAuthenticationToken token = (PlatAuthenticationToken)authentication;
		return loadUser(token,true);
	}


	public UserDetails loadUser(PlatAuthenticationToken token,boolean checkVerifyCode){
		PlatUserLoginService userLoginService = userLoginServiceContext.get(PlatUserLoginService.SERVICE_ID + String.valueOf(token.getLoginModel().getLoginType()));
		if(userLoginService != null){
			return userLoginService.loadUser(token,checkVerifyCode);
		}
		throw new ServiceException("不支持的登陆方式");
	}

}
