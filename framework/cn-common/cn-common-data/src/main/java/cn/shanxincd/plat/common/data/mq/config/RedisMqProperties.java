package cn.shanxincd.plat.common.data.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.mq.redis")
public class RedisMqProperties {
    private int retryTimes  = 3;
    private int maxConsumer = 5;
}