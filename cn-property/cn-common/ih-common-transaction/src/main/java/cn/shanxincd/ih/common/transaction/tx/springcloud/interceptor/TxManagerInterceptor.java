package cn.shanxincd.ih.common.transaction.tx.springcloud.interceptor;

import com.codingapi.tx.aop.service.AspectBeforeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@AllArgsConstructor
public class TxManagerInterceptor {
	private final AspectBeforeService aspectBeforeService;

	@SneakyThrows
	public Object around(ProceedingJoinPoint point) {
		RequestAttributes requestAttributes;
		String groupId = null;
		try {
			requestAttributes = RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			groupId = request.getHeader("tx-group");
		} catch (IllegalStateException e) {
			log.info("非web 环境调用 不包含请求上下文");
		}

		return aspectBeforeService.around(groupId, point);
	}
}
