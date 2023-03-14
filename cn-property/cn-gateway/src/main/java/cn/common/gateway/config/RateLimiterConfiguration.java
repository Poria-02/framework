package cn.common.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 路由限流配置
 */
@Configuration
public class RateLimiterConfiguration {

	@Bean(value = "remoteAddrKeyResolver")
	public KeyResolver remoteAddrKeyResolver() {
		//根据IP限流，可以自定义
		return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
	}

}
