package cn.upms.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.upms.api.entity.SysTreeDict;
import cn.upms.api.entity.SysTreeDictItem;
import cn.upms.api.enums.DictionaryType;
import cn.upms.api.enums.ItemStats;
import cn.upms.api.vo.DictQueryModel;
import cn.upms.biz.mapper.SysTreeDictItemMapper;
import cn.upms.biz.mapper.SysTreeDictMapper;
import cn.upms.biz.service.SysTreeDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.datascope.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * (SysTreeDict)表服务实现类
 *
 * @author makejava
 * @since 2020-07-03 09:24:53
 */
@Service
public class SysTreeDictServiceImpl extends ServiceImpl<SysTreeDictMapper, SysTreeDict> implements SysTreeDictService {

    @Autowired
    private SysTreeDictMapper treeDictMapper;

    @Autowired
    private SysTreeDictItemMapper treeDictItemMapper;

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Optional<SysTreeDict> findById(String id) {
        return Optional.ofNullable(treeDictMapper.selectById(id));
    }

    /**
     * 通过ID查询用户字典
     *
     * @param id
     * @return
     */
    @Override
    public Optional<SysTreeDict> findUserDictById(String id) {
        return Optional.ofNullable(treeDictMapper.findById(id, new DataScope()));
    }

    /**
     * 通过Code查询
     *
     * @param code
     * @return
     */
    @Override
    public Optional<SysTreeDict> findByCode(String code) {
        LambdaQueryWrapper<SysTreeDict> where = Wrappers.lambdaQuery();
        where.eq(SysTreeDict::getCode, code);
        where.last("limit 1");
        return Optional.ofNullable(treeDictMapper.selectOne(where));
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public IPage<SysTreeDict> page(DictQueryModel queryModel) {
        LambdaQueryWrapper<SysTreeDict> where = Wrappers.lambdaQuery();
        where.like(StrUtil.isNotBlank(queryModel.getCode()), SysTreeDict::getCode, queryModel.getCode());
        where.like(StrUtil.isNotBlank(queryModel.getName()), SysTreeDict::getName, queryModel.getName());
        where.eq(StrUtil.isNotBlank(queryModel.getType()), SysTreeDict::getType, queryModel.getType());

        IPage<SysTreeDict> page = new Page<>(queryModel.getCurrent(), queryModel.getSize());
        return this.page(page, where);

    }

    @Override
    public List<DictionaryType> getAllType() {
        return DictionaryType.ALL;
    }


    @Override
    public List<SysTreeDictItem> findItems(String dictId, String name) {
        LambdaQueryWrapper<SysTreeDictItem> where = Wrappers.lambdaQuery();
        where.eq(SysTreeDictItem::getDictId, dictId)
                .orderByAsc(SysTreeDictItem::getSort);
        if (!StrUtil.isEmpty(name)) {
            where.likeRight(SysTreeDictItem::getName, name);
        }

        return treeDictItemMapper.selectList(where);
    }

    @Override
    public List<SysTreeDictItem> findItemsByPid(String dictId, String pid) {
        LambdaQueryWrapper<SysTreeDictItem> where = Wrappers.lambdaQuery();
        where.eq(SysTreeDictItem::getDictId, dictId);
        where.eq(SysTreeDictItem::getPid, pid);
        where.orderByAsc(SysTreeDictItem::getSort);
        return treeDictItemMapper.selectList(where);
    }

    @Override
    public Optional<SysTreeDictItem> findItem(String id) {
        return Optional.ofNullable(treeDictItemMapper.selectById(id));
    }

    @Override
    public SysTreeDictItem saveItem(SysTreeDictItem item) {
        item.setIsDelete(ItemStats.OK);
        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());
        treeDictItemMapper.insert(item);
        return item;
    }

    @Override
    public SysTreeDictItem updateItem(SysTreeDictItem item) {
        item.setUpdateTime(new Date());
        treeDictItemMapper.updateById(item);
        return item;
    }

}