package cn.upms.biz.service.impl;

import cn.common.core.utils.Assert;
import cn.upms.api.entity.SysTag;
import cn.upms.biz.mapper.SysTagMapper;
import cn.upms.biz.service.SysTagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 标签分类(SysTag)表服务实现类
 *
 * @author makejava
 * @since 2020-08-27 14:35:03
 */
@Service
public class SysTagServiceImpl extends ServiceImpl<SysTagMapper, SysTag> implements SysTagService {

    /**
     * 保存标签
     *
     * @param sysTag
     */
    @Override
    public SysTag saveTag(SysTag sysTag) {

        //查询key是否重复
        Assert.isNull(selectByKey(sysTag.getTagKey()), "key:{}已存在", sysTag.getTagKey());
        this.save(sysTag);
        return sysTag;

    }

    @Override
    public SysTag selectByKey(String key) {
        return this.getOne(new LambdaQueryWrapper<SysTag>().eq(SysTag::getTagKey, key));
    }
}