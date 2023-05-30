package cn.shanxincd.plat.common.data.conver;

import cn.hutool.core.collection.CollectionUtil;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.data.conver.annotation.Dict;
import cn.shanxincd.plat.common.data.conver.annotation.Dicts;
import cn.shanxincd.plat.common.data.conver.model.DictItem;
import cn.upms.biz.api.vo.TreeDictItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class DictAspect {
	private final ExpressionParser parser = new SpelExpressionParser();

	@Autowired
	private RestTemplate restTemplate;

	private final String URL = "http://unhm-upms:4000/dict/items/";

	private final String TREE_URL = "http://unhm-upms:4000/treedict/item/list/";

	@Around("@annotation(dicts)")
	public Object invoked(ProceedingJoinPoint point, Dicts dicts) throws Throwable {
		Object obj = point.proceed();
		Object result = obj;
		if (obj instanceof R) {
			//返回结果类型是R 时才处理
			result = ((R) obj).getData();
		}
		//多个字段循环处理
		if (dicts.value().length == 0) {
			log.error("字典规则未配置");
		}

		for (Dict dict : dicts.value()) {
			dictHandle(result, dict);
		}

		return obj;
	}

	@Around("@annotation(dict)")
	public Object invoked(ProceedingJoinPoint point, Dict dict) throws Throwable {
		Object obj = point.proceed();
		Object result = obj;
		if (obj instanceof R) {
			//返回结果类型是R 时才处理
			result = ((R) obj).getData();

		}
		dictHandle(result, dict);
		return obj;
	}

	private void dictHandle(Object object, Dict dict) {
		if(object == null){
			return;
		}
		String key = dict.key();
		String value = dict.value();
		String dictCode = dict.dictCode();
		Map map = null;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add(SecurityConstants.FROM, SecurityConstants.FROM_IN);
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

		if (DictType.ITEM.equals(dict.dictType())) {
			ResponseEntity<R<List<DictItem>>> response =
					restTemplate.exchange(URL + dictCode, HttpMethod.GET, requestEntity,
							new ParameterizedTypeReference<R<List<DictItem>>>() {});
			if (response.getBody().isSuccess()) {
				R<List<DictItem>> r = response.getBody();
				if (CollectionUtil.isNotEmpty(r.getData())) {
					map = r.getData().stream().collect(Collectors.toMap(DictItem::getValue, DictItem::getLabel, (k1, k2) -> k1));
				}
			}

		} else if (DictType.TREE.equals(dict.dictType())) {
			ResponseEntity<R<List<TreeDictItemVo>>> response =
					restTemplate.exchange(TREE_URL + dictCode, HttpMethod.GET, requestEntity,
							new ParameterizedTypeReference<R<List<TreeDictItemVo>>>() {});
			if (response.getBody().isSuccess()) {
				R<List<TreeDictItemVo>> r = response.getBody();
				if (CollectionUtil.isNotEmpty(r.getData())) {
					map = r.getData().stream().collect(Collectors.toMap(TreeDictItemVo::getValue, TreeDictItemVo::getName, (k1, k2) -> k1));
				}
			}
		}

		convertValues(map, object, dict);
	}

	private void convertValues(Map<String, String> map, Object object, Dict dict) {
		if (CollectionUtils.isEmpty(map)) {
			return;
		}

		if (object instanceof IPage) {
			IPage data = (IPage) object;
			if (!CollectionUtils.isEmpty(data.getRecords())) {
				for (Object obj : data.getRecords()) {
					convertValue(map, obj, dict);
				}
			}
		} else if (object instanceof List) {
			List data = (List) object;
			if (!CollectionUtils.isEmpty(data)) {
				for (Object obj : data) {
					convertValue(map, obj, dict);
				}
			}
		} else {
			convertValue(map, object, dict);
		}
	}

	private void convertValue(Map<String, String> map, Object obj, Dict dict) {
		Expression keyExpression = parser.parseExpression(dict.key());
		String key = keyExpression.getValue(obj, String.class);
		if (map.get(key) != null) {
			Expression valueExpression = parser.parseExpression(dict.value());
			valueExpression.setValue(obj, map.get(key));
		}
	}
}
