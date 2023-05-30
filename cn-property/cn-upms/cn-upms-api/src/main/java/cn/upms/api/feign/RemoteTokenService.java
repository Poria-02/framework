package cn.upms.api.feign;

import cn.common.core.constant.SecurityConstants;
import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "remoteTokenService", url = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {
    /**
     * 分页查询token 信息
     *
     * @param from   内部调用标志
     * @param params 分页参数
     * @param from   内部调用标志
     * @return page
     */
    @PostMapping("/token/page")
    R<Page> getTokenPage(@RequestBody Map<String, Object> params, @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 删除token
     *
     * @param from  内部调用标志
     * @param token token
     * @param from  内部调用标志
     * @return
     */
    @DeleteMapping("/token/{token}")
    R<Boolean> removeTokenById(@PathVariable("token") String token, @RequestHeader(SecurityConstants.FROM) String from);
}
