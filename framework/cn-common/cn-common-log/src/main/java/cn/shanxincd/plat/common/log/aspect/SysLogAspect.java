package cn.shanxincd.plat.common.log.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.shanxincd.plat.common.log.annotation.SysLog;
import cn.shanxincd.plat.common.log.event.SysLogEvent;
import cn.shanxincd.plat.common.log.util.SysLogUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 操作日志使用spring event异步入库
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class SysLogAspect {
	private final ApplicationEventPublisher publisher;

	@SneakyThrows
	@Around("@annotation(sysLog)")
	public Object around(ProceedingJoinPoint point, SysLog sysLog) {
		String strClassName  = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

		cn.shanxincd.plat.common.log.service.SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(sysLog.value());

		//获取请求参数
		String params = JSONUtil.toJsonStr(point.getArgs());

		if(StrUtil.isBlank(params)){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			params = JSONUtil.toJsonStr(request.getParameterMap());
		}
		logVo.setParams(params);
		//发送异步日志事件
		Long startTime 	= System.currentTimeMillis();
		Object obj 		= point.proceed();
		Long endTime 	= System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		publisher.publishEvent(new SysLogEvent(logVo));
		return obj;
	}
}
