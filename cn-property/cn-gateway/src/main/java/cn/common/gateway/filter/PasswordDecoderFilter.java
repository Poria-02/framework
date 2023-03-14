package cn.common.gateway.filter;

import cn.common.core.constant.SecurityConstants;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 密码解密工具类
 * 参考 ModifyRequestBodyGatewayFilterFactory 实现
 */
@Slf4j
@Component
public class PasswordDecoderFilter extends AbstractGatewayFilterFactory {
	private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

	private static final String PASSWORD = "password";
	private static final String KEY_ALGORITHM = "AES";

	@Value("${security.encode.key:1234567812345678}")
	private String encodeKey;

	@Value("#{'${security.intercept.url:/oauth/token}'.split(',')}")
	private List<String> intercept;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			//不是登录请求,且不是需要被拦截的URL
			String path = request.getURI().getPath();
			log.info("path = {}, intercept = {}", path, intercept);
			if (!StrUtil.containsAnyIgnoreCase(path, SecurityConstants.OAUTH_TOKEN_URL) && !needIntercept(path)
					&& !StrUtil.containsAnyIgnoreCase(path, SecurityConstants.IH_LOGIN_URL)) {
				return chain.filter(exchange);
			}

			//刷新token，直接向下执行
			String grantType = request.getQueryParams().getFirst("grant_type");
			if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
				return chain.filter(exchange);
			}

			Class inClass  = String.class;
			Class outClass = String.class;
			ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

			//解密生成新的报文
			Mono<?> modifiedBody = serverRequest.bodyToMono(inClass).flatMap(decryptAES());

			BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
			HttpHeaders headers = new HttpHeaders();
			headers.putAll(exchange.getRequest().getHeaders());
			headers.remove(HttpHeaders.CONTENT_LENGTH);

			headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
			CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
			return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
				ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
				return chain.filter(exchange.mutate().request(decorator).build());
			}));
		};
	}

	/** 原文解密 */
	private Function decryptAES() {
		return s -> {
			//构建前端对应解密AES 因子
			AES aes = new AES(Mode.CBC, Padding.NoPadding, new SecretKeySpec(encodeKey.getBytes(), KEY_ALGORITHM), new IvParameterSpec(encodeKey.getBytes()));

			//获取请求密码并解密
			Map inParamsMap;
			String param = (String) s;

			//兼容JSON
			if (param.contains("{") && param.contains("}")) {
				inParamsMap = JSON.parseObject(param);
			}
			else {
				inParamsMap = HttpUtil.decodeParamMap(param, CharsetUtil.CHARSET_UTF_8);
			}

			if(inParamsMap.get(PASSWORD) != null && StrUtil.isNotBlank(inParamsMap.get(PASSWORD).toString())){
				byte[] result = aes.decrypt(Base64.decode(inParamsMap.get(PASSWORD).toString().getBytes(StandardCharsets.UTF_8)));
				String password = new String(result, StandardCharsets.UTF_8);

				//返回修改后报文字符
				inParamsMap.put(PASSWORD, password.trim());
			}

			if (param.contains("{") && param.contains("}")) {
				return Mono.just(JSON.toJSONString(inParamsMap));
			}
			else {
				return Mono.just(HttpUtil.toParams(inParamsMap));
			}
		};
	}

	/** 报文转换 */
	private ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
		return new ServerHttpRequestDecorator(exchange.getRequest()) {
			@Override
			public Flux<DataBuffer> getBody() {
				return outputMessage.getBody();
			}

			@Override
			public HttpHeaders getHeaders() {
				long contentLength = headers.getContentLength();
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.putAll(super.getHeaders());
				if (contentLength > 0) {
					httpHeaders.setContentLength(contentLength);
				}
				else {
					httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
				}

				return httpHeaders;
			}
		};
	}

	private boolean needIntercept(String path) {
		if (intercept == null || intercept.isEmpty()) {
			return false;
		}

		for (String iPath : intercept) {
			if (StrUtil.containsAnyIgnoreCase(iPath, path)) {
				return true;
			}
		}

		return false;
	}
}
