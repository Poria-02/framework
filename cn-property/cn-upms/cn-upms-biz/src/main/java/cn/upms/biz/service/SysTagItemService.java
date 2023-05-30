package cn.upms.biz.service;

import cn.upms.api.entity.SysTagItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 标签项(SysTagItem)表服务接口
 *
 * @author makejava
 * @since 2020-08-27 14:35:06
 */
public interface SysTagItemService extends IService<SysTagItem> {

	SysTagItem saveTagItem(SysTagItem sysTagItem);

	void updateTagItem(SysTagItem sysTagItem);

	List<SysTagItem> selectByKeys(String tagKey);
}