package cn.common.gateway.support;

import cn.common.core.constant.CacheConstants;
import cn.hutool.core.collection.CollUtil;
import cn.common.gateway.vo.RouteDefinitionVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * redis 保存路由信息，优先级比配置文件高
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

	private final RedisTemplate redisTemplate;

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return route.flatMap(r -> {
			RouteDefinitionVo vo = new RouteDefinitionVo();
			BeanUtils.copyProperties(r, vo);
			log.info("保存路由信息{}", vo);

			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.opsForHash().put(CacheConstants.ROUTE_KEY, r.getId(), vo);

			//通过Redis发布出去
			redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "新增路由信息,网关缓存更新");
			return Mono.empty();
		});
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		routeId.subscribe(id -> {
			log.info("删除路由信息{}", id);

			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.opsForHash().delete(CacheConstants.ROUTE_KEY, id);
		});

		redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "删除路由信息,网关缓存更新");
		return Mono.empty();
	}

	/**
	 * 动态路由入口
	 * 1. 先从内存中获取
	 * 2. 为空加载Redis中数据
	 * 3. 更新内存
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		List<RouteDefinitionVo> routeList = RouteCacheHolder.getRouteList();
		if (CollUtil.isNotEmpty(routeList)) {
			log.debug("内存 中路由定义条数： {}， {}", routeList.size(), routeList);
			return Flux.fromIterable(routeList);
		}

		//从Redis中加载路由信息
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVo.class));
		List<RouteDefinitionVo> values = redisTemplate.opsForHash().values(CacheConstants.ROUTE_KEY);
		log.debug("redis 中路由定义条数： {}， {}", values.size(), values);

		RouteCacheHolder.setRouteList(values);
		return Flux.fromIterable(values);
	}
}
