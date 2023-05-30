package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 标签分类(SysTag)表服务接口
 *
 * @author makejava
 * @since 2020-08-27 14:35:00
 */
public interface SysTagService extends IService<SysTag> {

	/**
	 * 保存标签
	 * @param sysTag
	 */
	SysTag saveTag(SysTag sysTag);

	SysTag selectByKey(String key);

}