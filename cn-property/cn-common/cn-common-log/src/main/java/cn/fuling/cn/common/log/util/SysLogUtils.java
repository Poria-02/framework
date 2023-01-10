package cn.fuling.cn.common.log.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * 系统日志工具类
 */
@UtilityClass
public class SysLogUtils {

//	public SysLog getSysLog() {
//		HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//
//		SysLog sysLog = new SysLog();
//		sysLog.setType(CommonConstants.STATUS_NORMAL);
//		sysLog.setCreateBy(Objects.requireNonNull(getUsername()));
//		sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
//		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
//		sysLog.setMethod(request.getMethod());
//		sysLog.setUserAgent(request.getHeader("user-agent"));
//		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
//		sysLog.setServiceId(getClientId());
//
//		return sysLog;
//	}
//
//	public SysLog getSysLog(HttpServletRequest request, String username) {
//		SysLog sysLog = new SysLog();
//
//		sysLog.setCreateBy(username);
//		sysLog.setType(CommonConstants.STATUS_NORMAL);
//		sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
//		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
//		sysLog.setMethod(request.getMethod());
//		sysLog.setUserAgent(request.getHeader("user-agent"));
//		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
//		sysLog.setServiceId(getClientId());
//
//		return sysLog;
//	}

	/**
	 * 获取客户端
	 */
	private String getClientId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}

		return null;
	}

	/**
	 * 获取用户名称
	 */
	private String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}

		return authentication.getName();
	}
}
