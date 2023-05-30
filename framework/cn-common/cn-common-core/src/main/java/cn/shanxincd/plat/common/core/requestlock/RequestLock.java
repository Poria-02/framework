package cn.shanxincd.plat.common.core.requestlock;

import java.lang.annotation.*;

/**
 * 根据key限制接口同时只允许一个请求执行<br>
 * key应为前缀加业务主键 保证唯一<br>
 * key支持SqEL 例如: "#orderId","#request.orderId","'ADD_ORDER_' + #req.orderId"
 * waitTime   等待多长时间获取锁(秒)
 * leaseTime  缓存锁存活时间(秒)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLock {
    String key();

    int waitTime() default 1;

    int leaseTime() default 180;
}
