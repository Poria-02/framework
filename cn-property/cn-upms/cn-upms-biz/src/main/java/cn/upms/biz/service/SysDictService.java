package cn.upms.biz.service;

import cn.common.core.utils.R;
import cn.upms.api.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典表
 */
public interface SysDictService extends IService<SysDict> {
    /**
     * 根据ID 删除字典
     */
    R removeDict(Integer id);

    /**
     * 更新字典
     */
    R updateDict(SysDict sysDict);
}
