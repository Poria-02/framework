package cn.upms.biz.controller;


import cn.common.core.constant.CacheConstants;
import cn.common.core.utils.Assert;
import cn.common.core.utils.R;
import cn.common.security.annotation.Inner;
import cn.common.security.utils.SecurityUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.shanxincd.plat.upms.api.vo.TreeDictItemModel;
import cn.upms.api.entity.SysTreeDict;
import cn.upms.api.entity.SysTreeDictItem;
import cn.upms.api.enums.DictionaryType;
import cn.upms.api.vo.TreeDictItemModel;
import cn.upms.api.vo.TreeDictItemVo;
import cn.upms.biz.service.SysTreeDictItemService;
import cn.upms.biz.service.SysTreeDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (SysTreeDictItem)表控制层
 *
 * @author makejava
 * @since 2020-07-03 09:24:56
 */
@RestController
@Tag(name = "分级字典管理")
@RequestMapping("/treedict/item")
public class SysTreeDictItemController {

    @Resource
    private SysTreeDictService treeDictService;

    @Resource
    private SysTreeDictItemService treeDictItemService;


    /**
     * 查询单个字典项
     *
     * @return
     */
    @Inner(value = false)
    @Operation(summary = "查询单个字典项", description = "查询单个字典项")
    @GetMapping("/getItem/{dictId}/{value}")
    public R<TreeDictItemVo> getDictItem(@PathVariable("dictId") String dictId, @PathVariable("value") String value) {
        SysTreeDictItem dictItem = treeDictItemService.getOne(new LambdaQueryWrapper<SysTreeDictItem>().eq(SysTreeDictItem::getDictId, dictId)
                .eq(SysTreeDictItem::getValue, value));
        TreeDictItemVo treeDictItemVo = new TreeDictItemVo();
        BeanUtil.copyProperties(dictItem, treeDictItemVo);
        return R.ok(treeDictItemVo);
    }

    /**
     * 返回树结构
     *
     * @param code
     * @return
     */
    @Inner(value = false)
    @Operation(summary = "查询字典项(返回树)", description = "查询字典项(返回树)")
    @GetMapping("/{code}")
    @Cacheable(value = CacheConstants.TREE_DICT_DETAILS_TREE, key = "#code", unless = "#result.data.isEmpty()")
    public R<List<TreeDictItemVo>> dictItemTrees(@PathVariable("code") String code) {
        Optional<SysTreeDict> dictOpt = treeDictService.findByCode(code);
        Assert.isTrue(dictOpt.isPresent(), "字典项不存在");

        List<SysTreeDictItem> list = treeDictService.findItems(dictOpt.get().getId(), null);
        List<TreeDictItemVo> beans = list.stream().map(item -> toDictItemBean(item)).collect(Collectors.toList());

        if (dictOpt.get().getIsTree() == 1) {
            beans = toTree(beans);
        }

        return R.ok(beans);
    }

    /**
     * 返回列表
     *
     * @param code
     * @return
     */
    @Inner(value = false)
    @Operation(summary = "查询字典项(返回列表)", description = "查询字典项(返回列表)")
    @GetMapping("/list/{code}")
    @Cacheable(value = CacheConstants.TREE_DICT_DETAILS_LIST, key = "#code", unless = "#result.data.isEmpty()")
    public R<List<TreeDictItemVo>> dictItems(@PathVariable("code") String code) {
        Optional<SysTreeDict> dictOpt = treeDictService.findByCode(code);
        Assert.isTrue(dictOpt.isPresent(), "字典项不存在");

        List<SysTreeDictItem> list = treeDictService.findItems(dictOpt.get().getId(), null);
        List<TreeDictItemVo> beans = list.stream().map(item -> toDictItemBean(item)).collect(Collectors.toList());
        return R.ok(beans);
    }

    @Operation(description = "添加字典项")
    @PostMapping("/add")
    @CacheEvict(value = {CacheConstants.TREE_DICT_DETAILS_LIST, CacheConstants.TREE_DICT_DETAILS_TREE}, allEntries = true)
    public R<SysTreeDictItem> add(@Validated @RequestBody TreeDictItemModel model) {
        Optional<SysTreeDict> dictOpt = treeDictService.findById(model.getDictId());
        Assert.isTrue(dictOpt.isPresent(), "字典不存在");
        Assert.isTrue(dictOpt.get().getType() == DictionaryType.CUSTOMER.getId(), "系统字典不允许修改");

        //校验字典value是否存在
        SysTreeDictItem local = treeDictItemService.getOne(new QueryWrapper<SysTreeDictItem>().eq("dict_id", model.getDictId()).eq("value", model.getValue()));
        Assert.isNull(local, "该字典项已存在:" + model.getValue());
        SysTreeDictItem item = toDictItem(model);
        item.setCreateBy(SecurityUtils.getUser().getId());
        item.setCreateTime(new Date());
        treeDictService.saveItem(item);

        return R.ok(item);
    }

    @Operation(description = "更新字典项")
    @PutMapping("/update")
    @CacheEvict(value = {CacheConstants.TREE_DICT_DETAILS_LIST, CacheConstants.TREE_DICT_DETAILS_TREE}, allEntries = true)
    public R<Boolean> updateItem(@Validated @RequestBody TreeDictItemModel req) {
        Optional<SysTreeDict> dictOpt = treeDictService.findById(req.getDictId());
        Assert.isTrue(dictOpt.isPresent(), "字典不存在");
        Assert.isTrue(dictOpt.get().getType() == DictionaryType.CUSTOMER.getId(), "系统字典不允许修改");

        Optional<SysTreeDictItem> itemOpt = treeDictService.findItem(req.getId());
        Assert.isTrue(itemOpt.isPresent(), "不存在");
        SysTreeDictItem old = itemOpt.get();
        old.setName(req.getName());
        old.setSimpleName(req.getSimpleName());
        old.setRemark(req.getRemark());
        if (!old.getValue().equals(req.getValue())) {
            //校验value是否存在
            SysTreeDictItem local = treeDictItemService.getOne(new QueryWrapper<SysTreeDictItem>().eq("dict_id", req.getDictId()).eq("value", req.getValue()));
            Assert.isNull(local, "该字典项已存在:" + req.getValue());
            old.setValue(req.getValue());
        }
        old.setExt1(req.getExt1());
        old.setUpdateTime(new Date());
        old.setUpdateBy(SecurityUtils.getUser().getId());
        old.setSort(req.getSort());
        treeDictService.updateItem(old);
        return R.ok(Boolean.TRUE);
    }

    @Operation(description = "删除字典项")
    @DeleteMapping("/del/{itemId}")
    @CacheEvict(value = {CacheConstants.TREE_DICT_DETAILS_LIST, CacheConstants.TREE_DICT_DETAILS_TREE}, allEntries = true)
    public R<Boolean> delItem(@PathVariable("itemId") String itemId) {

        Optional<SysTreeDictItem> itemOpt = treeDictService.findItem(itemId);
        Assert.isTrue(itemOpt.isPresent(), "字典项不存在");

        Optional<SysTreeDict> dictOpt = treeDictService.findById(itemOpt.get().getDictId());
        Assert.isTrue(dictOpt.isPresent(), "字典不存在");
        Assert.isTrue(dictOpt.get().getType() == DictionaryType.CUSTOMER.getId(), "系统字典不允许修改");

        treeDictItemService.removeById(itemOpt.get().getId());

        return R.ok(Boolean.TRUE);
    }

    @Operation(description = "查询树形字典")
    @GetMapping("/find_tree_items/{code}/{pid}")
    public R<List<TreeDictItemVo>> getTreeItems(
            @Parameter(name = "字典CODE") @PathVariable("code") String code,
            @Parameter(name = "父节点ID,如果为0代表一级节点") @PathVariable("pid") String pid
    ) {
        Optional<SysTreeDict> dictOpt = treeDictService.findByCode(code);
        Assert.isTrue(dictOpt.isPresent(), "不存在");

        List<SysTreeDictItem> items = treeDictService.findItemsByPid(dictOpt.get().getId(), pid);
        List<TreeDictItemVo> beans = items.stream().map(item -> toDictItemBean(item)).collect(Collectors.toList());

        return R.ok(beans);
    }

    @Operation(summary = "查询所有城市", description = "查询所有的市")
    @GetMapping("/cityList")
    @Cacheable(value = CacheConstants.TREE_DICT_DETAILS_CITY)
    public R<List<TreeDictItemVo>> cityList() {

        List<SysTreeDictItem> citys = treeDictItemService.list(new QueryWrapper<SysTreeDictItem>().eq("dict_id", "china_positon").eq("simple_name", "市"));

        return R.ok(citys.stream().map(vo -> toDictItemBean(vo)).collect(Collectors.toList()));

    }


    @Schema(description = "查询上级字典项")
    @Inner
    @GetMapping("/parent/{dictCode}/{value}")
    public R<TreeDictItemVo> getParentDictItem(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value) {
        SysTreeDictItem sysTreeDictItem = treeDictItemService.getParentDictItem(dictCode, value);
        TreeDictItemVo vo = new TreeDictItemVo();
        BeanUtils.copyProperties(sysTreeDictItem, vo);
        return R.ok(vo);
    }


    private List toTree(List<TreeDictItemVo> beans) {

        Map<String, TreeDictItemVo> map = beans.stream().collect(Collectors.toMap(TreeDictItemVo::getId, TreeDictItemVo -> TreeDictItemVo));

        for (TreeDictItemVo b : beans) {
            if (StrUtil.isNotBlank(b.getPid())) {
                TreeDictItemVo parent = map.get(b.getPid());
                if (parent != null) {
                    if (parent.getChilds() == null) {
                        parent.setChilds(new ArrayList<>());
                    }
                    parent.getChilds().add(b);
                }
            }
        }
        List<TreeDictItemVo> tree = new ArrayList<>();
        for (TreeDictItemVo b : beans) {
            if ("0".equals(b.getPid())) {
                tree.add(b);
            }
        }
        return tree;
    }

    private TreeDictItemVo toDictItemBean(SysTreeDictItem item) {
        TreeDictItemVo bean = new TreeDictItemVo();
        BeanUtils.copyProperties(item, bean);
        return bean;
    }

    private SysTreeDictItem toDictItem(TreeDictItemModel model) {
        SysTreeDictItem item = new SysTreeDictItem();
        BeanUtils.copyProperties(model, item);
        return item;
    }
}
