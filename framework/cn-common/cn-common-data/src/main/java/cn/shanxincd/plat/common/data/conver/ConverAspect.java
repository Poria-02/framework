package cn.shanxincd.plat.common.data.conver;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.data.conver.annotation.ConverData;
import cn.shanxincd.plat.common.data.conver.annotation.ConverDatas;
import cn.shanxincd.plat.common.data.conver.dao.ConverDao;
import cn.shanxincd.plat.common.data.conver.model.ConverReq;
import cn.shanxincd.plat.common.data.conver.model.ConverResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ConverAspect {

	private ExpressionParser parser = new SpelExpressionParser();

	@Autowired
	private RestTemplate restTemplate;

	@Resource
	private Environment environment;

	@Autowired(required = false)
	private ConverDao converDao;

	private final String URL = "/inner/converData";

	@Around("@annotation(converData)")
	public Object invoked(ProceedingJoinPoint point, ConverDatas converData) throws Throwable {
		Object obj = point.proceed();
		Object converObj = obj;
		if(converObj instanceof R){
			//返回结果类型是R 时才处理
			converObj = ((R) obj).getData();
		}
		//多个字段循环处理
		if(converData.value().length == 0){
			log.error("ConverDatas 未配置转换规则。");
		}
		for(ConverData converFData : converData.value()){
			converOneField(converObj, converFData);
		}
		return obj;
	}


	@Around("@annotation(converData)")
	public Object invoked(ProceedingJoinPoint point, ConverData converData) throws Throwable {
		Object obj = point.proceed();
		if(obj instanceof R){
			Object result = ((R) obj).getData();
			converOneField(result, converData);
		}
		return obj;
	}


	private void converOneField(Object result, ConverData converData){
		if(result == null){
			return;
		}
		Set<String> keys = convertKeys(result, converData.key());
		//调用服务接口查询结果
		if(!CollectionUtils.isEmpty(keys)){

			//如果serviceId为空 查询本地数据
			String dbKey = StrUtil.isBlank(converData.dbKey())?lastSymbolCase(converData.key()): converData.dbKey();
			String dbVaule = StrUtil.isBlank(converData.dbValue())?
					StrUtil.isBlank(converData.value())?lastSymbolCase(converData.key().replace("Id","Name")):lastSymbolCase(converData.value())
					: converData.dbValue();
			log.debug("数据库查询字段信息-- dbKey:{},dbValue:{}",dbKey,dbVaule);
			Map map = null;
			if(StrUtil.isBlank(converData.serviceId())){
				if(converDao == null){
					log.error("数据转换不支持本地查询,请配置MapperScan");
					return;
				}
				List<ConverResult> list = converDao.convertData(keys, converData.table(),dbKey,dbVaule);
				if (CollectionUtils.isEmpty(list)) {
					return;
				}
				map =  list.stream().collect(Collectors.toMap(ConverResult::getKey, ConverResult::getValue,(k1, k2)->k1));
			}else{
				//调用远程接口查询数据
				ConverReq req = new ConverReq(dbKey,dbVaule, converData.table(),keys);
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.add(SecurityConstants.FROM, SecurityConstants.FROM_IN);
				HttpEntity<ConverReq> requestEntity = new HttpEntity<ConverReq>(req, requestHeaders);
				ResponseEntity<R<Map<String,String>>> response = restTemplate.exchange( environment.resolvePlaceholders(converData.serviceId()) + URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<R<Map<String,String>>>(){});
				if(response.getBody().isSuccess()){
					R<Map<String,String>> r = response.getBody();
					if(CollectionUtil.isNotEmpty(r.getData())){
						map = r.getData();
					}
				}
			}
			convertValues(map,result, converData);
		}
	}


	/**
	 * 从结果中读取要处理的key
	 * @param object
	 * @param spel
	 * @return
	 */
	private Set<String> convertKeys(Object object,String spel){

		Set<String> keys = new HashSet<>();
		if(object instanceof IPage){
			IPage data = (IPage)object;
			if(!CollectionUtils.isEmpty(data.getRecords())){
				for (Object obj : data.getRecords()) {
					Expression expression = parser.parseExpression(spel);
					String key = expression.getValue(obj, String.class);
					keys.add(key);
				}
			}
		}else if(object instanceof List){
			List data = (List)object;
			if(!CollectionUtils.isEmpty(data)){
				for (Object obj : data) {
					Expression expression = parser.parseExpression(spel);
					String key = expression.getValue(obj, String.class);
					keys.add(key);
				}
			}
		}else {
			Expression expression = parser.parseExpression(spel);
			String key = expression.getValue(object, String.class);
			keys.add(key);
		}
		log.info("获取keys:{}",keys);
		return keys;
	}

	private void convertValues(Map<String,String> map, Object object, ConverData converData){

		if(CollectionUtils.isEmpty(map)){
			return;
		}
		if(object instanceof IPage){
			IPage data = (IPage)object;
			if(!CollectionUtils.isEmpty(data.getRecords())){
				for (Object obj : data.getRecords()) {
					convertValue(map,obj, converData);
				}
			}
		}else if(object instanceof List){
			List data = (List)object;
			if(!CollectionUtils.isEmpty(data)){
				for (Object obj : data) {
					convertValue(map,obj, converData);
				}
			}
		}else {
			convertValue(map,object, converData);
		}
	}

	private void convertValue(Map<String,String> map, Object obj, ConverData converData){
		Expression keyExpression = parser.parseExpression(converData.key());
		String key = keyExpression.getValue(obj, String.class);
		if(map.get(key) != null){
			Expression valueExpression ;
			if(StrUtil.isBlank(converData.value())){
				valueExpression = parser.parseExpression(converData.key().replace("Id","Name"));
			}else {
				valueExpression = parser.parseExpression(converData.value());
			}
			if(valueExpression != null){
				valueExpression.setValue(obj,map.get(key));
			}
		}
	}

	private String lastSymbolCase(String str){
		if( StrUtil.isBlank(str)){
			return null;
		}
		String[] strings = str.split("\\.");
		return StrUtil.toSymbolCase(strings[strings.length-1],'_');
	}


}
