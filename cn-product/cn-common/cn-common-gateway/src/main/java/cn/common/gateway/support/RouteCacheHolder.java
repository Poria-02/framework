package cn.common.gateway.support;

import cn.common.gateway.vo.RouteDefinitionVo;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由缓存工具类
 */
@UtilityClass
public class RouteCacheHolder {
	private final Cache<String, RouteDefinitionVo> cache = CacheUtil.newLFUCache(50);

	/**
	 * 清空缓存
	 */
	public void removeRouteList() {
		cache.clear();
	}

	/**
	 * 获取缓存的全部对象
	 * @return routeList
	 */
	public List<RouteDefinitionVo> getRouteList() {
		List<RouteDefinitionVo> routeList = new ArrayList<>();
		cache.forEach(routeList::add);

		return routeList;
	}

	/**
	 * 更新缓存
	 * @param routeList 缓存列表
	 */
	public void setRouteList(List<RouteDefinitionVo> routeList) {
		routeList.forEach(route -> cache.put(route.getId(), route));
	}
}
