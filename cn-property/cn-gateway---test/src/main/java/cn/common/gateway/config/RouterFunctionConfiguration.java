package cn.common.gateway.config;

//工具包配置
import cn.common.gateway.handler.ImageCodeHandler;
import cn.common.gateway.handler.SwaggerUiHandler;
import cn.common.gateway.handler.SwaggerResourceHandler;
import cn.common.gateway.handler.SwaggerSecurityHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * 路由配置信息
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RouterFunctionConfiguration {
	private final ImageCodeHandler imageCodeHandler;

	private final SwaggerUiHandler swaggerUiHandler;
	private final SwaggerResourceHandler swaggerResourceHandler;
	private final SwaggerSecurityHandler swaggerSecurityHandler;

	@Bean
	public RouterFunction routerFunction() {
		//验证码和Swagger放在网关处理
		return RouterFunctions
			//验证码
			.route(RequestPredicates.path("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), imageCodeHandler)

			//swagger-resource处理类
			.andRoute(RequestPredicates.GET("/swagger-resources").and(RequestPredicates.accept(MediaType.ALL)), swaggerResourceHandler)
			.andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui").and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
			.andRoute(RequestPredicates.GET("/swagger-resources/configuration/security").and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);
	}
}
