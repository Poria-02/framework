package cn.common.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 版本校验处理
 */
@Slf4j
@Component
public class VersionGatewayFilter extends AbstractGatewayFilterFactory {

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			//校验版本通过
			if (checkVersion(exchange)) {
				return chain.filter(exchange);
			}
			//校验版本不通过
			else {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UPGRADE_REQUIRED);	//需要强制升级
				return response.setComplete();
			}
		};
	}

	/**
	 * 校验版本
	 */
	private boolean checkVersion(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String version = request.getHeaders().getFirst("VERSION");
		log.info("客户端版本信息为：{}", version);

		//当前版本
		Object versionObj = redisTemplate.opsForValue().get("current_version");
		log.info("服务器端版本信息为：{}", versionObj);

		return true;
	}
}
