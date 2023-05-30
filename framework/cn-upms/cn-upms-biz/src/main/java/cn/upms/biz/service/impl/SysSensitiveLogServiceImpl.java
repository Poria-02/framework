package cn.upms.biz.service.impl;

import cn.hutool.json.JSONUtil;
import cn.shanxincd.plat.common.security.util.SecurityUtils;
import cn.upms.biz.dao.SysSensitiveLogDao;
import cn.upms.biz.api.dto.SensitiveInfo;
import cn.upms.biz.api.entity.SysSensitiveLog;
import cn.upms.biz.service.SysSensitiveLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (SysSensitiveLog)表服务实现类
 *
 * @author makejava
 * @since 2021-01-27 21:17:40
 */
@Service
public class SysSensitiveLogServiceImpl extends ServiceImpl<SysSensitiveLogDao, SysSensitiveLog> implements SysSensitiveLogService {

	@Override
	public void saveSensitiveLog(SensitiveInfo sensitiveInfo) {

		SysSensitiveLog sysSensitiveLog = new SysSensitiveLog();
		sysSensitiveLog.setCreateName(SecurityUtils.getUser().getUsername());
		sysSensitiveLog.setCreateBy(SecurityUtils.getSId());
		sysSensitiveLog.setUserType(sensitiveInfo.getUserType());
		sysSensitiveLog.setSensitiveInfo(JSONUtil.toJsonStr(sensitiveInfo));
		this.save(sysSensitiveLog);

	}
}