package cn.upms.biz.service;

import cn.shanxincd.plat.common.core.dto.RouteDto;
import cn.upms.biz.api.entity.SysRouteConf;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 路由
 */
public interface SysRouteConfService extends IService<SysRouteConf> {

	/**
	 * 更新路由信息
	 * @param routes 路由信息
	 */
	Mono<Void> updateRoutes(List<RouteDto> routes);
}
