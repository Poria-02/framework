package cn.upms.biz.service.impl;

import cn.common.core.constant.CacheConstants;
import cn.common.core.utils.R;
import cn.shanxincd.plat.common.core.constant.enums.DictTypeEnum;
import cn.upms.api.entity.SysDict;
import cn.upms.api.entity.SysDictItem;
import cn.upms.biz.mapper.SysDictItemMapper;
import cn.upms.biz.service.SysDictItemService;
import cn.upms.biz.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 字典项
 */
@Service
@AllArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {
    private final SysDictService dictService;

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R removeDictItem(Integer id) {
        //根据ID查询字典ID
        SysDictItem dictItem = this.getById(id);
        SysDict dict = dictService.getById(dictItem.getDictId());

        //系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典项目不能删除");
        }

        return R.ok(this.removeById(id));
    }

    /**
     * 更新字典项
     *
     * @param item 字典项
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R updateDictItem(SysDictItem item) {
        //查询字典
        SysDict dict = dictService.getById(item.getDictId());

        //系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典项目不能删除");
        }

        return R.ok(this.updateById(item));
    }

    /**
     * 根据字典类型  字典项值（唯一） 获取字典项数据
     *
     * @param dictCode
     * @param value
     * @return
     */
    @Override
    public SysDictItem findDictItem(String dictCode, String value) {
        SysDict dict = dictService.getOne(new LambdaQueryWrapper<SysDict>().eq(SysDict::getType, dictCode));
        return this.getOne(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictId, dict.getId()).eq(SysDictItem::getValue, value));
    }
}
