package cn.upms.api.feign;

import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "remoteParamService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteParamService {

    @GetMapping("/param/publicValue/{publicKey}")
    R<String> publicKey(@PathVariable("publicKey") String publicKey);

}
