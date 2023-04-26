package cn.common.security.aspect;

import cn.common.core.constant.SecurityConstants;
import cn.common.core.exception.InnerException;
import cn.common.core.utils.ServletUtils;
import cn.common.core.utils.StringUtils;
import cn.common.security.annotation.Inner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 内部服务调用验证处理
 *
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered {
    @Around("@annotation(inner)")
    public Object innerAround(ProceedingJoinPoint point, Inner inner) throws Throwable {

        String source = ServletUtils.getRequest().getHeader(SecurityConstants.FROM_SOURCE);
        // 内部请求验证
        if (!StringUtils.equals(SecurityConstants.INNER, source)) {
            throw new InnerException("没有内部访问权限，不允许访问");
        }

        String userid = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID);
        String username = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USERNAME);
        // 用户信息验证
        if (inner.isUser() && (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username))) {
            throw new InnerException("没有设置用户信息，不允许访问 ");
        }
        return point.proceed();
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
