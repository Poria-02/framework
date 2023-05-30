package cn.shanxincd.plat.common.data.mq.core;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisListener {
    /**
     * 消费的queue
     */
    String[] queues();

    /**
     * 容器工厂
     */
    String containerFactory() default "";
}
