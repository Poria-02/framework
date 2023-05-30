package cn.upms.biz.controller;

import cn.common.core.constant.SecurityConstants;
import cn.common.core.utils.R;
import cn.upms.api.feign.RemoteTokenService;
import com.ruoyi.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * getTokenPage 管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/token")
@Tag(name = "token", description = "令牌管理模块")
public class SysTokenController {
    private final RemoteTokenService remoteTokenService;

    /**
     * 分页token 信息
     *
     * @param params 参数集
     * @return token集合
     */
    @GetMapping("/page")
    public R getTokenPage(@RequestParam Map<String, Object> params) {
        return remoteTokenService.getTokenPage(params, SecurityConstants.FROM_IN);
    }

    /**
     * 删除
     *
     * @param token getTokenPage
     * @return success/false
     */
    @SysLog("删除用户token")
    @DeleteMapping("/{token}")
    @PreAuthorize("@pms.hasPermission('sys_token_del')")
    public R removeById(@PathVariable String token) {
        return remoteTokenService.removeTokenById(token, SecurityConstants.FROM_IN);
    }
}
