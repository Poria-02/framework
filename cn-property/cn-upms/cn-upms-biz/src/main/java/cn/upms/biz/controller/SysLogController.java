package cn.upms.biz.controller;

import cn.common.core.utils.R;
import cn.common.security.annotation.Inner;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.security.annotation.Inner;
import cn.shanxincd.plat.upms.api.entity.SysLog;
import cn.shanxincd.plat.upms.api.vo.PreLogVO;
import cn.shanxincd.plat.upms.service.SysLogService;
import cn.upms.api.entity.SysLog;
import cn.upms.biz.service.SysLogService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志表 前端控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Tag(name = "log", description = "日志管理模块")
public class SysLogController {
	private final SysLogService sysLogService;

	/**
	 * 简单分页查询
	 * @param page   分页对象
	 * @param sysLog 系统日志
	 */
	@GetMapping("/page")
	public R getLogPage(Page page, SysLog sysLog) {
		return R.ok(sysLogService.page(page, Wrappers.query(sysLog)));
	}

	/**
	 * 删除日志
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_log_del')")
	public R removeById(@PathVariable Long id) {
		return R.ok(sysLogService.removeById(id));
	}

	/**
	 * 插入日志
	 * @param sysLog 日志实体
	 * @return success/false
	 */
	@Inner
	@PostMapping("/save")
	public R save(@Valid @RequestBody SysLog sysLog) {
		return R.ok(sysLogService.save(sysLog));
	}

	/**
	 * 批量插入前端异常日志
	 * @param preLogVoList 日志实体
	 * @return success/false
	 */
	@PostMapping("/logs")
	public R saveBatchLogs(@RequestBody List<PreLogVO> preLogVoList) {
		return R.ok(sysLogService.saveBatchLogs(preLogVoList));
	}
}
