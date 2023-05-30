package cn.shanxincd.plat.common.data.conver;

import cn.hutool.core.util.StrUtil;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.data.conver.annotation.DefaultValue;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Aspect
@Component
public class DefaultValueAspect {

	private final ExpressionParser parser = new SpelExpressionParser();

	@Autowired
	private RestTemplate restTemplate;

	private final String URL = "http://unhm-upms:4000/param/publicValue/";

	@Around("@annotation(defaultValue)")
	public Object invoked(ProceedingJoinPoint point, DefaultValue defaultValue) throws Throwable {
		Object obj = point.proceed();
		if (obj instanceof R) {
			//返回结果类型是R 时才处理
			Object result = ((R) obj).getData();
			defaultValueHandle(result, defaultValue);
		} else {
			log.error("不支持的返回结果");
		}
		return obj;
	}

	private void  defaultValueHandle(Object result,DefaultValue defaultValue){
		if(result == null){
			return;
		}
		String key = defaultValue.key();

		//根据key 查询默认类型参数值
		ResponseEntity<R> response = restTemplate.getForEntity(URL + key,R.class);
		Object value = response.getBody().getData();
		if(value == null){
			return;
		}
		//将默认值赋值到结果上
		if (result instanceof IPage) {
			IPage data = (IPage) result;
			if (!CollectionUtils.isEmpty(data.getRecords())) {
				for (Object obj : data.getRecords()) {
					convertValue(value, obj, defaultValue);
				}
			}
		} else if (result instanceof List) {
			List data = (List) result;
			if (!CollectionUtils.isEmpty(data)) {
				for (Object obj : data) {
					convertValue(value, obj, defaultValue);
				}
			}
		} else {
			convertValue(value, result, defaultValue);
		}

	}


	private void convertValue(Object value, Object obj, DefaultValue defaultValue) {
		Expression valueExpression = parser.parseExpression(defaultValue.field());
		Object object = valueExpression.getValue(obj);
		if(object == null || (object instanceof String && StrUtil.isBlank((String)object))){
			valueExpression.setValue(obj, value);
		}
	}

}
