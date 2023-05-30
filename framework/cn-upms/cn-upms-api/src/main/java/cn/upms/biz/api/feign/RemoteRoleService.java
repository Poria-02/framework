package cn.upms.biz.api.feign;

import cn.upms.biz.api.entity.SysRole;
import cn.shanxincd.plat.common.core.constant.SecurityConstants;
import cn.shanxincd.plat.common.core.constant.ServiceNameConstants;
import cn.shanxincd.plat.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "remoteRoleService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteRoleService {

	/**
	 * 根据角色code查询角色信息
	 * @param code   code
	 * @param from   调用标志
	 * @return R
	 */
	@GetMapping("/role/code/{code}")
	R<SysRole> byCode(@PathVariable("code") String code, @RequestHeader(SecurityConstants.FROM) String from);
}
