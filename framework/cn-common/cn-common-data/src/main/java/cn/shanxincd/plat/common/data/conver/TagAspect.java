package cn.shanxincd.plat.common.data.conver;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.data.conver.annotation.Tag;
import cn.shanxincd.plat.common.data.conver.annotation.Tags;
import cn.shanxincd.plat.common.data.conver.model.TagItem;
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
public class TagAspect {
	private final ExpressionParser parser = new SpelExpressionParser();

	@Autowired
	private RestTemplate restTemplate;

	private final String URL = "http://unhm-upms:4000/tagItem/items/";

	@Around("@annotation(tags)")
	public Object invoked(ProceedingJoinPoint point, Tags tags) throws Throwable {
		Object obj = point.proceed();
		if (obj instanceof R) {
			//返回结果类型是R 时才处理
			Object result = ((R) obj).getData();

			//多个字段循环处理
			if (tags.value().length == 0) {
				log.error("字典规则未配置");
			}

			for (Tag tag : tags.value()) {
				tagHandle(result, tag);
			}
		} else {
			log.error("不支持的返回结果");
		}
		return obj;
	}

	@Around("@annotation(tag)")
	public Object invoked(ProceedingJoinPoint point, Tag tag) throws Throwable {
		Object obj = point.proceed();
		if (obj instanceof R) {
			//返回结果类型是R 时才处理
			Object result = ((R) obj).getData();
			tagHandle(result, tag);
		} else {
			log.error("不支持的返回结果");
		}
		return obj;
	}

	private void tagHandle(Object object, Tag tag) {
		if(object == null){
			return;
		}
		String tagKey = tag.tagKey();
		Map map = null;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add(SecurityConstants.FROM, SecurityConstants.FROM_IN);
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

			ResponseEntity<R<List<TagItem>>> response =
					restTemplate.exchange(URL + tagKey, HttpMethod.GET, requestEntity,
							new ParameterizedTypeReference<R<List<TagItem>>>() {});
			if (response.getBody().isSuccess()) {
				R<List<TagItem>> r = response.getBody();
				if (CollectionUtil.isNotEmpty(r.getData())) {
					map = r.getData().stream().collect(Collectors.toMap(TagItem::getValue, TagItem->TagItem));
				}
			}

		convertValues(map, object, tag);
	}

	private void convertValues(Map<String, TagItem> map, Object object, Tag tag) {
		if (CollectionUtils.isEmpty(map)) {
			return;
		}

		if (object instanceof IPage) {
			IPage data = (IPage) object;
			if (!CollectionUtils.isEmpty(data.getRecords())) {
				for (Object obj : data.getRecords()) {
					convertValue(map, obj, tag);
				}
			}
		} else if (object instanceof List) {
			List data = (List) object;
			if (!CollectionUtils.isEmpty(data)) {
				for (Object obj : data) {
					convertValue(map, obj, tag);
				}
			}
		} else {
			convertValue(map, object, tag);
		}
	}

	private void convertValue(Map<String, TagItem> map, Object obj, Tag tag) {
		Expression keyExpression = parser.parseExpression(tag.key());
		String key = keyExpression.getValue(obj, String.class);
		if (map.get(key) != null) {
			Expression valueExpression = parser.parseExpression(tag.value());
			JSONObject json = JSONUtil.parseObj(map.get(key));
			String value = json.getStr(tag.itemKey());
			valueExpression.setValue(obj, value);
		}
	}
}
