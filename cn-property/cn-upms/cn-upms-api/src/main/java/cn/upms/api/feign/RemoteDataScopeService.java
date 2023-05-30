package cn.upms.api.feign;

import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import cn.upms.api.entity.SysDeptRelation;
import cn.upms.api.entity.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程数据权限调用接口
 */
@FeignClient(name = "remoteDataScopeService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteDataScopeService {

    /**
     * 通过角色ID 查询角色列表
     *
     * @param roleIdList 角色ID
     */
    @PostMapping("/role/getRoleList")
    R<List<SysRole>> getRoleList(@RequestBody List<String> roleIdList);

    /**
     * 获取子级部门
     *
     * @param deptId 部门ID
     */
    @GetMapping("/dept/getDescendantList/{deptId}")
    R<List<SysDeptRelation>> getDescendantList(@PathVariable("deptId") Long deptId);
}
