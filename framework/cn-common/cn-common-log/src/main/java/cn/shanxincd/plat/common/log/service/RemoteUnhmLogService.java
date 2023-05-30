package cn.shanxincd.plat.common.log.service;



/**
 * @author zhangchunlei
 * @date 2023年02月27日 2:46 PM
 */
public interface RemoteUnhmLogService {
	void saveLog(SysLog sysLog, String fromIn);
}
