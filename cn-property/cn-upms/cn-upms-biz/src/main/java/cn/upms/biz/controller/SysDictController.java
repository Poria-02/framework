package cn.upms.biz.controller;

import cn.common.core.constant.CacheConstants;
import cn.common.core.utils.R;
import cn.common.security.annotation.Inner;
import cn.hutool.core.util.StrUtil;
import cn.upms.api.entity.SysDict;
import cn.upms.api.entity.SysDictItem;
import cn.upms.biz.service.SysDictItemService;
import cn.upms.biz.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 字典表 前端控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Tag(name = "dict", description = "字典管理模块")
public class SysDictController {
    private final SysDictService sysDictService;
    private final SysDictItemService sysDictItemService;

    /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysDictService.getById(id));
    }

    /**
     * 分页查询字典信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage> getDictPage(Page page, SysDict sysDict) {
        return R.ok(sysDictService.page(page, Wrappers.query(sysDict)));
    }

    /**
     * 通过字典类型查找字典
     *
     * @param type 类型
     * @return 同类型字典
     */
    @GetMapping("/type/{type}")
    @Cacheable(value = CacheConstants.DICT_DETAILS, key = "#type", unless = "#result.data.isEmpty()")
    public R getDictByType(@PathVariable String type) {
        return R.ok(sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getType, type)));
    }

    /**
     * 添加字典
     *
     * @param sysDict 字典信息
     * @return success、false
     */
    @SysLog("添加字典")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_dict_add')")
    public R save(@Valid @RequestBody SysDict sysDict) {
        return R.ok(sysDictService.save(sysDict));
    }

    /**
     * 删除字典，并且清除字典缓存
     *
     * @param id ID
     * @return R
     */
    @SysLog("删除字典")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    public R removeById(@PathVariable Integer id) {
        return sysDictService.removeDict(id);
    }

    /**
     * 修改字典
     *
     * @param sysDict 字典信息
     * @return success/false
     */
    @PutMapping
    @SysLog("修改字典")
    @PreAuthorize("@pms.hasPermission('sys_dict_edit')")
    public R updateById(@Valid @RequestBody SysDict sysDict) {
        return sysDictService.updateDict(sysDict);
    }

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param sysDictItem 字典项
     */
    @GetMapping("/item/page")
    public R getSysDictItemPage(Page page, SysDictItem sysDictItem) {
        page.addOrder(OrderItem.desc("sort"));
        return R.ok(sysDictItemService.page(page, new LambdaQueryWrapper<SysDictItem>()
                .eq(sysDictItem.getDictId() != null, SysDictItem::getDictId, sysDictItem.getDictId())
                .eq(StrUtil.isNotBlank(sysDictItem.getType()), SysDictItem::getType, sysDictItem.getType())
                .like(StrUtil.isNotBlank(sysDictItem.getLabel()), SysDictItem::getLabel, sysDictItem.getLabel())));
    }

    @Inner
    @Hidden
    @GetMapping("/items/{type}")
    public R getSysDictItems(@PathVariable("type") String type) {
        return R.ok(sysDictItemService.list(new QueryWrapper<SysDictItem>().eq("type", type)));
    }

    @Inner
    @Hidden
    @GetMapping("/item/{dictCode}/{value}")
    public R<SysDictItem> findDictItem(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value) {
        return R.ok(sysDictItemService.findDictItem(dictCode, value));
    }

    @Operation(summary = "查询指定字典项", description = "查询指定字典项")
    @GetMapping("/api/item/{dictCode}/{value}")
    public R<SysDictItem> findItemValue(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value) {
        return R.ok(sysDictItemService.findDictItem(dictCode, value));
    }

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return R
     */
    @GetMapping("/item/{id}")
    public R getDictItemById(@PathVariable("id") Integer id) {
        return R.ok(sysDictItemService.getById(id));
    }

    /**
     * 新增字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @SysLog("新增字典项")
    @PostMapping("/item")
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R save(@RequestBody SysDictItem sysDictItem) {
        return R.ok(sysDictItemService.save(sysDictItem));
    }

    /**
     * 修改字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @SysLog("修改字典项")
    @PutMapping("/item")
    public R updateById(@RequestBody SysDictItem sysDictItem) {
        return sysDictItemService.updateDictItem(sysDictItem);
    }

    /**
     * 通过id删除字典项
     *
     * @param id id
     * @return R
     */
    @SysLog("删除字典项")
    @DeleteMapping("/item/{id}")
    public R removeDictItemById(@PathVariable Integer id) {
        return sysDictItemService.removeDictItem(id);
    }
}
