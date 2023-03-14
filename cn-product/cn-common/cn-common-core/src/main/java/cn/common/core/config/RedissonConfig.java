package cn.common.core.config;

import lombok.Setter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Setter
@Configuration
@ConfigurationProperties(prefix="spring.redis")
@ConditionalOnProperty(name = "spring.redis.host")
public class RedissonConfig {

    private String host;

    private String port;

    private String password;

    @Bean
    public RedissonClient redisson() throws IOException {

        Config config = new Config();
        String address = "redis://" + host + ":" + port;
        config.useSingleServer().setAddress(address).setPassword(password)
            .setConnectionMinimumIdleSize(5);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
