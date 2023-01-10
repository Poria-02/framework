package cn.fuling.cn.common.log.event;


import cn.fuling.cn.common.log.annotation.SysLog;
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
