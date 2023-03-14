package cn.common.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * API统计
 */
@Slf4j
@Component
public class ApiLogGatewayFilter extends AbstractGatewayFilterFactory {

	private final String FRONT_FLAG = "APP";

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			record(exchange);
			return chain.filter(exchange);
		};
	}

	/**
	 * 记录API访问
	 */
	@Async
	public void record(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();

		String flag = request.getHeaders().getFirst("FLAG");
		log.debug("客户端标识：{}", flag);
		if (FRONT_FLAG.equals(flag)) {

		}

		//获取API，获取参数？
		String path = request.getURI().getPath();
		//缓存
		//批量入库
	}
}
