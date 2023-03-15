package cn.momet.gateway.handler;

import java.io.IOException;

import cn.momet.core.exception.CaptchaException;
import cn.momet.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import cn.momet.gateway.service.ValidateCodeService;
import reactor.core.publisher.Mono;

/**
 * 验证码获取
 *
 * @author ruoyi
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {
	@Autowired
	private ValidateCodeService validateCodeService;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		AjaxResult ajax;
		try {
			ajax = validateCodeService.createCaptcha();
		} catch (CaptchaException | IOException e) {
			return Mono.error(e);
		}
		return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
	}
}
