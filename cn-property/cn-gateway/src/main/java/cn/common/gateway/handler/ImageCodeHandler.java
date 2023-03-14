package cn.common.gateway.handler;

import cn.shanxincd.ih.common.core.constant.CacheConstants;
import cn.shanxincd.ih.common.core.constant.SecurityConstants;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 验证码生成逻辑处理类
 */
@Slf4j
@Component
@AllArgsConstructor
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {
	private static final Integer DEFAULT_IMAGE_HEIGHT = 40;
	private static final Integer DEFAULT_IMAGE_WIDTH  = 100;

	private final RedisTemplate redisTemplate;

	@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD,
		RequestMethod.PUT, RequestMethod.PATCH}, origins = "*"
	)
	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		ArithmeticCaptcha captcha = new ArithmeticCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
		String result = captcha.text();

		//保存验证码信息
		String randomStr = serverRequest.queryParam("randomStr").get();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.opsForValue().set(CacheConstants.DEFAULT_CODE_KEY + randomStr, result, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

		//转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		captcha.out(os);

		return ServerResponse
			.status(HttpStatus.OK)
			.contentType(MediaType.IMAGE_JPEG)
			.body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
	}
}
