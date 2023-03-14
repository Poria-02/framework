package cn.common.log.event;


import cn.common.log.annotation.SysLog;
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
