package cn.upms.biz.controller;

import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.log.annotation.SysLog;
import cn.shanxincd.plat.common.security.annotation.Inner;
import cn.upms.biz.api.entity.SysOauthClientDetails;
import cn.upms.biz.service.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 前端控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Tag(name = "客户端管理模块", description = "客户端管理模块")
public class SysClientController {
	private final SysOauthClientDetailsService clientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId clientId
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public R getByClientId(@PathVariable String clientId) {
		return R.ok(clientDetailsService.list(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId)));
	}

	/**
	 * 简单分页查询
	 * @param page                  分页对象
	 * @param sysOauthClientDetails 系统终端
	 */
	@GetMapping("/page")
	public R getOauthClientDetailsPage(Page page, SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(clientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
	}

	/**
	 * 添加
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@SysLog("添加终端")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public R add(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(clientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除
	 * @param clientId ID
	 * @return success/false
	 */
	@SysLog("删除终端")
	@DeleteMapping("/{clientId}")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R removeById(@PathVariable String clientId) {
		return R.ok(clientDetailsService.removeByClientId(clientId));
	}

	/**
	 * 编辑
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@SysLog("编辑终端")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public R update(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(clientDetailsService.updateClientById(sysOauthClientDetails));
	}
	@SysLog("清除终端缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R clearClientCache() {
		clientDetailsService.clearClientCache();
		return R.ok();
	}

	@Inner
	@GetMapping("/getClientDetailsById/{clientId}")
	public R getClientDetailsById(@PathVariable String clientId) {
		return R.ok(clientDetailsService.getOne(
				Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId), false));
	}

}
