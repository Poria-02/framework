package cn.common.gateway.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 灰度路由
 */
public interface GrayLoadBalancer {
	/**
	 * 根据serviceId 筛选可用服务
	 * @param serviceId 服务ID
	 * @param request   当前请求
	 */
	ServiceInstance choose(String serviceId, ServerHttpRequest request);
}
