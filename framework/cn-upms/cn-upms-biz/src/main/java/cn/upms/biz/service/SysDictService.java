package cn.upms.biz.service;

import cn.shanxincd.plat.common.core.util.R;
import cn.upms.biz.api.entity.SysDict;
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
