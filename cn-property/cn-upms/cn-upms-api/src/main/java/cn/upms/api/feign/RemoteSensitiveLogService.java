package cn.upms.api.feign;

import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import cn.upms.api.dto.SensitiveInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "remoteSensitiveLogService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteSensitiveLogService {

    @PostMapping("/sensitive/log")
    R saveSensitiveLog(@RequestBody @Validated SensitiveInfo sensitiveInfo);

}
