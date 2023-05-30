package cn.shanxincd.plat.common.log.event;


import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.log.service.RemoteUnhmLogService;
import cn.shanxincd.plat.common.log.service.SysLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {
	private final RemoteUnhmLogService remoteLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLog sysLog = event.getSysLog();
		remoteLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
	}
}
