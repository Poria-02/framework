package cn.upms.biz.api.feign;

import cn.upms.biz.api.dto.SensitiveInfo;

import cn.shanxincd.plat.common.core.constant.ServiceNameConstants;
import cn.shanxincd.plat.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "remoteSensitiveLogService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteSensitiveLogService {

	@PostMapping("/sensitive/log")
	R saveSensitiveLog(@RequestBody @Validated SensitiveInfo sensitiveInfo);

}
