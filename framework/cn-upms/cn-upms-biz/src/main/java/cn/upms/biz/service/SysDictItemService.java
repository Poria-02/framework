package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.shanxincd.plat.common.core.util.R;

/**
 * 字典项
 */
public interface SysDictItemService extends IService<SysDictItem> {
	/**
	 * 删除字典项
	 */
	R removeDictItem(Integer id);

	/**
	 * 更新字典项
	 */
	R updateDictItem(SysDictItem item);

	SysDictItem findDictItem(String dictCode, String value);
}
