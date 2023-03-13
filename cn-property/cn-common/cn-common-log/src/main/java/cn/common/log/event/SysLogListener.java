package cn.common.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {
//	private final RemoteLogService remoteLogService;
//
//	@Async
//	@Order
//	@EventListener(SysLogEvent.class)
//	public void saveSysLog(SysLogEvent event) {
//		SysLog sysLog = event.getSysLog();
//		remoteLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
//	}
}
