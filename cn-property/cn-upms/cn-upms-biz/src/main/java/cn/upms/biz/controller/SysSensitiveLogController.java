package cn.upms.biz.controller;


import cn.common.core.utils.R;
import cn.upms.api.dto.SensitiveInfo;
import cn.upms.api.entity.SysSensitiveLog;
import cn.upms.biz.service.SysSensitiveLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysSensitiveLog)表控制层
 *
 * @author makejava
 * @since 2021-01-27 21:17:40
 */
@RestController
@Tag(name = "敏感信息查询日志")
@RequestMapping("/sensitive/log")
public class SysSensitiveLogController {

    @Resource
    private SysSensitiveLogService sysSensitiveLogService;


    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R selectAll(Page<SysSensitiveLog> page, SysSensitiveLog sysSensitiveLog) {
        return R.ok(sysSensitiveLogService.page(page, new QueryWrapper<>(sysSensitiveLog)));
    }


    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{id}")
    public R selectOne(@PathVariable String id) {
        return R.ok(sysSensitiveLogService.getById(id));
    }


    @PostMapping
    @Hidden
    public R saveSensitiveLog(@RequestBody @Validated SensitiveInfo sensitiveInfo) {
        sysSensitiveLogService.saveSensitiveLog(sensitiveInfo);
        return R.ok();
    }
}