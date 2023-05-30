/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package cn.shanxincd.plat.common.gateway.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import cn.shanxincd.plat.common.core.constant.CacheConstants;
import cn.shanxincd.plat.common.core.dto.RouteDto;
import cn.shanxincd.plat.common.gateway.vo.RouteDefinitionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2018/10/31
 * <p>
 * redis 保存路由信息，优先级比配置文件高
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

	private final RedisTemplate redisTemplate;

	private final SwaggerUiConfigProperties swaggerUiConfigProperties;

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return route.flatMap(r -> {
			RouteDefinitionVo vo = new RouteDefinitionVo();
			BeanUtils.copyProperties(r, vo);
			log.info("保存路由信息{}", vo);
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.opsForHash().put(CacheConstants.ROUTE_KEY, r.getId(), vo);
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
	 * <p>
	 * 1. 先从内存中获取 2. 为空加载Redis中数据 3. 更新内存
	 * @return
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		List<RouteDefinitionVo> routeList = RouteCacheHolder.getRouteList();
		if (CollUtil.isNotEmpty(routeList)) {
			log.debug("内存 中路由定义条数： {}， {}", routeList.size(), routeList);
			return Flux.fromIterable(routeList);
		}

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDto.class));
		List<RouteDto> values = redisTemplate.opsForHash().values(CacheConstants.ROUTE_KEY);
		routeList = RouteCacheHolder.setRouteList(values);
		log.info("redis 中路由定义条数： {}， {}", values.size(), JSONUtil.toJsonStr(values));

		swaggerUiConfigProperties.setUrls(routeList.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
				.filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
				.map(predicateDefinition ->
						swaggerUrl(routeDefinition.getRouteName(),
								predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", "")
						)
				)).collect(Collectors.toSet()));
		log.info("刷新路由:{}", JSONUtil.toJsonStr(routeList));
		return Flux.fromIterable(routeList);

	}


	private AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl(String name , String path){
		AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
		swaggerUrl.setName(name);
		swaggerUrl.setUrl(String.format( "%s/v3/api-docs", path));
		return swaggerUrl;
	}

}
