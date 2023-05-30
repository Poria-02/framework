package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysLog;
import cn.upms.biz.api.vo.PreLogVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 日志表 服务类
 */
public interface SysLogService extends IService<SysLog> {
	/**
	 * 批量插入前端错误日志
	 * @param preLogVoList 日志信息
	 * @return true/false
	 */
	Boolean saveBatchLogs(List<PreLogVO> preLogVoList);
}
