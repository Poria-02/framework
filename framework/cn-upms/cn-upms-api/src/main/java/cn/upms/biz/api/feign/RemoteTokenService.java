package cn.upms.biz.api.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.constant.ServiceNameConstants;
import cn.shanxincd.plat.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "remoteTokenService", url = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {
	/**
	 * 分页查询token 信息
	 * @param from   内部调用标志
	 * @param params 分页参数
	 * @param from   内部调用标志
	 * @return page
	 */
	@PostMapping("/token/page")
	R<Page> getTokenPage(@RequestBody Map<String, Object> params, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 删除token
	 * @param from  内部调用标志
	 * @param token token
	 * @param from  内部调用标志
	 * @return
	 */
	@DeleteMapping("/token/{token}")
	R<Boolean> removeTokenById(@PathVariable("token") String token, @RequestHeader(SecurityConstants.FROM) String from);
}
