package cn.momet.auth.service;

import cn.momet.core.constant.Constants;
import cn.momet.core.constant.SecurityConstants;
import cn.momet.core.utils.StringUtils;
import cn.momet.core.utils.ip.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.momet.system.api.RemoteLogService;
import cn.momet.system.api.domain.SysLogininfor;

/**
 * 记录日志方法
 *
 * @author ruoyi
 */
@Component
public class SysRecordLogService {
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 记录登录信息
	 *
	 * @param username 用户名
	 * @param status   状态
	 * @param message  消息内容
	 * @return
	 */
	public void recordLogininfor(String username, String status, String message) {
		SysLogininfor logininfor = new SysLogininfor();
		logininfor.setUserName(username);
		logininfor.setIpaddr(IpUtils.getIpAddr());
		logininfor.setMsg(message);
		// 日志状态
		if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
			logininfor.setStatus(Constants.LOGIN_SUCCESS_STATUS);
		} else if (Constants.LOGIN_FAIL.equals(status)) {
			logininfor.setStatus(Constants.LOGIN_FAIL_STATUS);
		}
		remoteLogService.saveLogininfor(logininfor, SecurityConstants.INNER);
	}
}
