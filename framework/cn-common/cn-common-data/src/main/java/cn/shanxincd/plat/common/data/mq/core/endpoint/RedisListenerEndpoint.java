package cn.shanxincd.plat.common.data.mq.core.endpoint;

import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

@Data
public class RedisListenerEndpoint {
    private String[] queueName;

    private String id;
    private Object bean;
    private Method method;

    private RedisTemplate<String, String> redisTemplate;
}