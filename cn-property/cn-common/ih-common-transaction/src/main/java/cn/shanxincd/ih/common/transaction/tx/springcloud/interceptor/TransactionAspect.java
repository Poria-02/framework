package cn.shanxincd.ih.common.transaction.tx.springcloud.interceptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * LCN 事务拦截器
 */
@Slf4j
@Aspect
@Component
public class TransactionAspect implements Ordered {
	@Autowired
	private TxManagerInterceptor txManagerInterceptor;

	@SneakyThrows
	@Around("@annotation(com.codingapi.tx.annotation.TxTransaction)")
	public Object transactionRunning(ProceedingJoinPoint point) {
		log.debug("annotation-TransactionRunning-start---->");
		Object obj = txManagerInterceptor.around(point);
		log.debug("annotation-TransactionRunning-end---->");
		return obj;
	}

	@SneakyThrows
	@Around("this(com.codingapi.tx.annotation.ITxTransaction) && execution( * *(..))")
	public Object around(ProceedingJoinPoint point) {
		log.debug("interface-ITransactionRunning-start---->");
		Object obj = txManagerInterceptor.around(point);
		log.debug("interface-ITransactionRunning-end---->");
		return obj;
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
}
