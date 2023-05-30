package cn.upms.biz.service;

import cn.upms.api.entity.SysTreeDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (SysTreeDictItem)表服务接口
 *
 * @author makejava
 * @since 2020-07-03 09:24:56
 */
public interface SysTreeDictItemService extends IService<SysTreeDictItem> {


    /**
     * 查询一级科室
     *
     * @param value
     * @return
     */
    SysTreeDictItem getParentDictItem(String dictCode, String value);
}