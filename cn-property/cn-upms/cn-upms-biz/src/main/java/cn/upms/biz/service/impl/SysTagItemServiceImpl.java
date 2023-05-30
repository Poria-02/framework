package cn.upms.biz.service.impl;

import cn.common.core.constant.CacheConstants;
import cn.common.core.utils.Assert;
import cn.hutool.core.util.StrUtil;
import cn.upms.api.entity.SysTag;
import cn.upms.api.entity.SysTagItem;
import cn.upms.biz.mapper.SysTagItemMapper;
import cn.upms.biz.service.SysTagItemService;
import cn.upms.biz.service.SysTagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签项(SysTagItem)表服务实现类
 *
 * @author makejava
 * @since 2020-08-27 14:35:06
 */
@Service
public class SysTagItemServiceImpl extends ServiceImpl<SysTagItemMapper, SysTagItem> implements SysTagItemService {

    @Autowired
    private SysTagService tagService;


    @Override
    public List<SysTagItem> selectByKeys(String tagKey) {
        SysTag tag = tagService.selectByKey(tagKey);
        Assert.notNull(tag, "标签{}分类不存在", tagKey);

        return this.list(new LambdaQueryWrapper<SysTagItem>().eq(SysTagItem::getTagId, tag.getId()));
    }

    @Override
    @CacheEvict(value = CacheConstants.TAG_ITEMS, allEntries = true)
    public SysTagItem saveTagItem(SysTagItem sysTagItem) {
        Assert.isNull(selectByTagIdAndValue(sysTagItem.getTagId(), sysTagItem.getValue()), "标签值:{}已存在", sysTagItem.getValue());
        this.save(sysTagItem);

        return sysTagItem;
    }

    @Override
    @CacheEvict(value = CacheConstants.TAG_ITEMS, allEntries = true)
    public void updateTagItem(SysTagItem sysTagItem) {

        //查询本地
        SysTagItem local = this.getById(sysTagItem.getId());
        Assert.notNull(local, "标签不存在");

        if (!StrUtil.equals(sysTagItem.getValue(), local.getValue())) {
            Assert.isNull(selectByTagIdAndValue(sysTagItem.getTagId(), sysTagItem.getValue()), "标签值:{}已存在", sysTagItem.getValue());
        }
        this.updateById(sysTagItem);
    }


    public SysTagItem selectByTagIdAndValue(String tagId, String value) {
        return this.getOne(new LambdaQueryWrapper<SysTagItem>().eq(SysTagItem::getTagId, tagId).eq(SysTagItem::getValue, value));
    }

}