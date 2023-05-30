package cn.shanxincd.plat.common.log.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.shanxincd.plat.common.core.constant.CommonConstants;
import cn.shanxincd.plat.common.core.util.IPUtils;
import cn.shanxincd.plat.common.log.service.SysLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * 系统日志工具类
 */
@UtilityClass
public class SysLogUtils {

	public SysLog getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

		SysLog sysLog = new SysLog();
		sysLog.setType(CommonConstants.STATUS_NORMAL);
		sysLog.setCreateBy(Objects.requireNonNull(getUsername()));
		sysLog.setRemoteAddr(IPUtils.getIpAddr(request));
		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));


		return sysLog;
	}

	public SysLog getSysLog(HttpServletRequest request, String username) {
		SysLog sysLog = new SysLog();

		sysLog.setCreateBy(username);
		sysLog.setType(CommonConstants.STATUS_NORMAL);
		sysLog.setRemoteAddr(IPUtils.getIpAddr(request));
		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
		return sysLog;
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
