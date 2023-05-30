package cn.upms.biz.service;


import cn.upms.biz.api.dto.SensitiveInfo;
import cn.upms.biz.api.entity.SysSensitiveLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (SysSensitiveLog)表服务接口
 *
 * @author makejava
 * @since 2021-01-27 21:17:40
 */
public interface SysSensitiveLogService extends IService<SysSensitiveLog> {

	/**
	 * 保存敏感日志
	 * @param sensitiveInfo
	 */
	void saveSensitiveLog(SensitiveInfo sensitiveInfo);
}