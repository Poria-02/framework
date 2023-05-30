package cn.upms.biz.service.impl;

import cn.hutool.json.JSONUtil;
import cn.shanxincd.plat.common.core.constant.CacheConstants;
import cn.shanxincd.plat.common.core.constant.CommonConstants;
import cn.shanxincd.plat.common.core.dto.RouteDto;
import cn.shanxincd.plat.common.core.event.DynamicRouteInitEvent;
import cn.upms.biz.api.entity.SysRouteConf;
import cn.upms.biz.mapper.SysRouteConfMapper;
import cn.upms.biz.service.SysRouteConfService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态路由处理类
 */
@Slf4j
@AllArgsConstructor
@Service
public class SysRouteConfServiceImpl extends ServiceImpl<SysRouteConfMapper, SysRouteConf> implements SysRouteConfService {
	private final RedisTemplate redisTemplate;
	private final ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 更新路由信息
	 * @param routes 路由信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Mono<Void> updateRoutes(List<RouteDto> routes) {
		//清空Redis 缓存
		Boolean result = redisTemplate.delete(CacheConstants.ROUTE_KEY);
		log.info("清空网关路由 {} ", result);

		//遍历修改的routes，保存到Redis
//		List<RouteDefinitionVo> routeDefinitionVoList = new ArrayList<>();
		try {
			routes.forEach(value -> {
//				log.info("更新路由 ->{}", value);
//				RouteDefinitionVo vo = new RouteDefinitionVo();
//				Map<String, Object> map = (Map) value;
//
//				Object id = map.get("routeId");
//				if (id != null) {
//					vo.setId(String.valueOf(id));
//				}
//
//				Object routeName = map.get("routeName");
//				if (routeName != null) {
//					vo.setRouteName(String.valueOf(routeName));
//				}
//
//				Object predicates = map.get("predicates");
//				if (predicates != null) {
//					JSONArray predicatesArray = (JSONArray) predicates;
//					List<PredicateDefinition> predicateDefinitionList = predicatesArray.toList(PredicateDefinition.class);
//					vo.setPredicates(predicateDefinitionList);
//				}
//
//				Object filters = map.get("filters");
//				if (filters != null) {
//					JSONArray filtersArray = (JSONArray) filters;
//					List<FilterDefinition> filterDefinitionList = filtersArray.toList(FilterDefinition.class);
//					vo.setFilters(filterDefinitionList);
//				}
//
//				Object uri = map.get("uri");
//				if (uri != null) {
//					vo.setUri(URI.create(String.valueOf(uri)));
//				}
//
//				Object order = map.get("order");
//				if (order != null) {
//					vo.setOrder(Integer.parseInt(String.valueOf(order)));
//				}

				redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDto.class));
				redisTemplate.opsForHash().put(CacheConstants.ROUTE_KEY, value.getId(), value);
			});

			//逻辑删除全部
			SysRouteConf condition = new SysRouteConf();
			condition.setDelFlag(CommonConstants.STATUS_NORMAL);
			this.remove(new UpdateWrapper<>(condition));

			//插入生效路由
			List<SysRouteConf> routeConfList = routes.stream().map(vo -> {
				SysRouteConf routeConf = new SysRouteConf();
				routeConf.setRouteId(vo.getRouteId());
				routeConf.setRouteName(vo.getRouteName());
				routeConf.setFilters(JSONUtil.toJsonStr(vo.getFilters()));
				routeConf.setPredicates(JSONUtil.toJsonStr(vo.getPredicates()));
				routeConf.setOrder(vo.getOrder());
				routeConf.setUri(vo.getUri().toString());
				return routeConf;
			}).collect(Collectors.toList());
			this.saveBatch(routeConfList);
			log.debug("更新网关路由结束 ");

			//发送路由刷新事件
//			this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
			redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "UPMS路由信息,网关缓存更新");
		}
		catch (Exception e) {
			log.error("路由配置解析失败", e);
			// 回滚路由，重新加载即可
			this.applicationEventPublisher.publishEvent(new DynamicRouteInitEvent(this));
			// 抛出异常
			throw new RuntimeException(e);
		}

		return Mono.empty();
	}
}
