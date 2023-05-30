package cn.upms.biz.controller;

import cn.shanxincd.plat.common.core.dto.RouteDto;
import cn.upms.biz.service.SysRouteConfService;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 路由
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@Tag(name = "route",description = "动态路由管理模块")
public class SysRouteConfController {
	private final SysRouteConfService sysRouteConfService;

	/**
	 * 获取当前定义的路由信息
	 */
	@GetMapping
	public R listRoutes() {
		return R.ok(sysRouteConfService.list());
	}

	/**
	 * 修改路由
	 * @param routes 路由定义
	 */
	@SysLog("修改路由")
	@PutMapping
	public R updateRoutes(@RequestBody List<RouteDto> routes) {
		return R.ok(sysRouteConfService.updateRoutes(routes));
	}
}
