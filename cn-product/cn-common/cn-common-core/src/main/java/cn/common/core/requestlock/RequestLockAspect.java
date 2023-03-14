package cn.common.core.requestlock;

import cn.common.core.exception.ServiceException;
import cn.hutool.core.util.StrUtil;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@ConditionalOnClass(RedissonClient.class)
public class RequestLockAspect {

    private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Autowired
    private RedissonClient redissonClient;

    @Around("@annotation(requestLock)")
    public Object invoked(ProceedingJoinPoint point,RequestLock requestLock) throws Throwable {
        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String spel = requestLock.key();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(spel);
        String key = expression.getValue(context, String.class);

        if(StrUtil.isBlank(key)){
            throw new ServiceException("无法处理当前请求");
        }

        RLock lock = redissonClient.getLock(key);
        Boolean isLock = lock.tryLock(requestLock.waitTime(),requestLock.leaseTime(),TimeUnit.SECONDS);
        if(!isLock){
            throw new ServiceException("当前已有正在处理的请求,请稍后再试");
        }
        //调用目标方法
        try{
            return point.proceed();
        }catch (Exception e){
        	log.info("异常信息",e);
            throw new ServiceException(e.getMessage());
        }finally {
            //释放锁
            lock.unlock();
        }
    }


}
