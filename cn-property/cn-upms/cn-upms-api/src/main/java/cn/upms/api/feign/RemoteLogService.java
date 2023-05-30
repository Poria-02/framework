package cn.upms.api.feign;

import cn.common.core.constant.SecurityConstants;
import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import cn.upms.api.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "remoteLogService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteLogService {
    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @param from   是否内部调用
     * @return succes、false
     */
    @PostMapping("/log/save")
    R<Boolean> saveLog(@RequestBody SysLog sysLog, @RequestHeader(SecurityConstants.FROM) String from);
}
