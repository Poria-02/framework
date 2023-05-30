package cn.upms.biz.controller;


import cn.common.core.constant.CacheConstants;
import cn.common.core.utils.R;
import cn.common.security.annotation.Inner;
import cn.upms.api.entity.SysTagItem;
import cn.upms.biz.service.SysTagItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签项(SysTagItem)表控制层
 *
 * @author makejava
 * @since 2020-08-27 14:43:58
 */
@RestController
@Tag(name = "标签项管理", description = "标签项管理")
@RequestMapping("/tagItem")
public class SysTagItemController {

    @Resource
    private SysTagItemService sysTagItemService;


    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R selectAll(Page<SysTagItem> page, SysTagItem sysTagItem) {
        return R.ok(sysTagItemService.page(page, new QueryWrapper<>(sysTagItem)));
    }

    @Operation(summary = "查询", description = "查询")
    @GetMapping("/list/{tagKey}")
    @Cacheable(value = CacheConstants.TAG_ITEMS, key = "#tagKey", unless = "#result.data.isEmpty()")
    public R<List<SysTagItem>> selectByKeys(@PathVariable("tagKey") String tagKey) {
        return R.ok(sysTagItemService.selectByKeys(tagKey));
    }


    @Inner
    @GetMapping("/items/{tagKey}")
    @Cacheable(value = CacheConstants.TAG_ITEMS, key = "#tagKey", unless = "#result.data.isEmpty()")
    public R<List<SysTagItem>> selectItemsByKeys(@PathVariable("tagKey") String tagKey) {
        return R.ok(sysTagItemService.selectByKeys(tagKey));
    }

    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{id}")
    public R selectOne(@PathVariable String id) {
        return R.ok(sysTagItemService.getById(id));
    }


    @PostMapping
    @SysLog("新增标签项")
    @Operation(summary = "新增标签项", description = "新增标签项 权限:admin_tagItem_add")
    @PreAuthorize("@pms.hasPermission('admin_tagItem_add')")
    public R<SysTagItem> insert(@RequestBody @Validated SysTagItem sysTagItem) {
        return R.ok(sysTagItemService.saveTagItem(sysTagItem));
    }


    @PutMapping
    @SysLog("修改标签项")
    @Operation(summary = "修改标签项", description = "修改标签项,权限admin_tagItem_edit ")
    @PreAuthorize("@pms.hasPermission('admin_tagItem_edit')")
    public R update(@RequestBody @Validated SysTagItem sysTagItem) {
        sysTagItemService.updateTagItem(sysTagItem);
        return R.ok();
    }


    @DeleteMapping("{id}")
    @SysLog("通过id删除标签项")
    @CacheEvict(value = CacheConstants.TAG_ITEMS, allEntries = true)
    @Operation(summary = "通过ID删除标签项", description = "通过ID删除标签项 权限：admin_tagItem_del")
    @PreAuthorize("@pms.hasPermission('admin_tagItem_del')")
    public R delete(@PathVariable String id) {
        sysTagItemService.removeById(id);
        return R.ok();
    }

}