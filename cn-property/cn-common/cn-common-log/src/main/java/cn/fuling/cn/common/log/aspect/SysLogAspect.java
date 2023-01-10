package cn.fuling.cn.common.log.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;

/**
 * 操作日志使用spring event异步入库
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class SysLogAspect {
//    private final ApplicationEventPublisher publisher;
//
//    @SneakyThrows
//    @Around("@annotation(sysLog)")
//    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
//        String strClassName = point.getTarget().getClass().getName();
//        String strMethodName = point.getSignature().getName();
//        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
//
//        cn.shanxincd.ih.admin.api.entity.SysLog logVo = SysLogUtils.getSysLog();
//        logVo.setTitle(sysLog.value());
//
//        //获取请求参数
//        String params = JSONUtil.toJsonStr(point.getArgs());
//
//        if (StrUtil.isBlank(params)) {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            params = JSONUtil.toJsonStr(request.getParameterMap());
//        }
//        logVo.setParams(params);
//        //发送异步日志事件
//        Long startTime = System.currentTimeMillis();
//        Object obj = point.proceed();
//        Long endTime = System.currentTimeMillis();
//        logVo.setTime(endTime - startTime);
//        publisher.publishEvent(new SysLogEvent(logVo));
//        return obj;
//    }
}
