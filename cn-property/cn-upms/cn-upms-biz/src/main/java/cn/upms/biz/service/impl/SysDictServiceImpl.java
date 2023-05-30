package cn.upms.biz.service.impl;

import cn.common.core.constant.CacheConstants;
import cn.common.core.utils.R;
import cn.shanxincd.plat.common.core.constant.enums.DictTypeEnum;
import cn.upms.api.entity.SysDict;
import cn.upms.api.entity.SysDictItem;
import cn.upms.biz.mapper.SysDictItemMapper;
import cn.upms.biz.mapper.SysDictMapper;
import cn.upms.biz.service.SysDictService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典表
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    private final SysDictItemMapper dictItemMapper;

    /**
     * 根据ID 删除字典
     *
     * @param id 字典IDS
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public R removeDict(Integer id) {
        SysDict dict = this.getById(id);
        //系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典不能删除");
        }

        baseMapper.deleteById(id);
        dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id));
        return R.ok();
    }

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    @Override
    public R updateDict(SysDict dict) {
        SysDict sysDict = this.getById(dict.getId());
        //系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(sysDict.getSystem())) {
            return R.failed("系统内置字典不能修改");
        }

        return R.ok(this.updateById(dict));
    }
}
