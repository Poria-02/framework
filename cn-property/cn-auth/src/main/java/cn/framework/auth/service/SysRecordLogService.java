package cn.framework.auth.service;



import cn.common.core.constant.CommonConstants;
import cn.common.core.constant.SecurityConstants;
import cn.common.core.utils.IpUtils;
import cn.umps.api.RemoteLogService;
import cn.umps.api.domain.SysLogininfor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if (StringUtils.equalsAny(status, CommonConstants.LOGIN_SUCCESS, CommonConstants.LOGOUT, CommonConstants.REGISTER)) {
            logininfor.setStatus(CommonConstants.LOGIN_SUCCESS_STATUS);
        } else if (CommonConstants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(CommonConstants.LOGIN_FAIL_STATUS);
        }
        remoteLogService.saveLogininfor(logininfor, SecurityConstants.INNER);
    }
}
