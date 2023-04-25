package cn.shanxincd.ih.common.transaction.tx.springcloud.feign;

import com.codingapi.tx.aop.bean.TxTransactionLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionRestTemplateInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {
		TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
		String groupId = txTransactionLocal == null ? null : txTransactionLocal.getGroupId();
		log.info("LCN-SpringCloud TxGroup info -> groupId:" + groupId);
		if (txTransactionLocal != null) {
			requestTemplate.header("tx-group", groupId);
		}
	}
}
