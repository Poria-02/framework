package cn.upms.biz.handler;

import cn.upms.api.dto.UserInfo;
import cn.upms.api.entity.SysUser;
import cn.upms.biz.service.SysUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SMS")
@AllArgsConstructor
public class SmsLoginHandler extends AbstractLoginHandler {
    private final SysUserService sysUserService;

    /**
     * 验证码登录传入为手机号
     * 不用不处理
     */
    @Override
    public String identify(String mobile) {
        return mobile;
    }

    /**
     * 通过 mobile 获取用户信息
     *
     * @return
     */
    @Override
    public UserInfo info(String identify) {
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, identify));
        if (user == null) {
            log.info("手机号未注册:{}", identify);
            return null;
        }

        return sysUserService.findUserInfo(user);
    }
}
