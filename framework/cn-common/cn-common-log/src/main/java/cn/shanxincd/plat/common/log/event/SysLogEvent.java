package cn.shanxincd.plat.common.log.event;

import cn.shanxincd.plat.common.log.service.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
	private final SysLog sysLog;
}
