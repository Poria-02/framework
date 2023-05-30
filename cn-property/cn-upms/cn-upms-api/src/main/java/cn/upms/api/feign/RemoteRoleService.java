package cn.upms.api.feign;

import cn.common.core.constant.SecurityConstants;
import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import cn.upms.api.entity.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "remoteRoleService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteRoleService {

    /**
     * 根据角色code查询角色信息
     *
     * @param code code
     * @param from 调用标志
     * @return R
     */
    @GetMapping("/role/code/{code}")
    R<SysRole> byCode(@PathVariable("code") String code, @RequestHeader(SecurityConstants.FROM) String from);
}
