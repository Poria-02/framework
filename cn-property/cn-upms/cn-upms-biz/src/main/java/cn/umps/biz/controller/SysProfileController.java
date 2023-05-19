package cn.umps.biz.controller;

import cn.common.core.web.controller.BaseController;
import cn.common.core.web.domain.AjaxResult;
import cn.common.security.service.TokenService;
import cn.common.security.utils.SecurityUtils;
import cn.umps.api.RemoteFileService;
import cn.umps.api.domain.SysUser;
import cn.umps.api.model.LoginUser;
import cn.umps.biz.service.SysUserService;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 个人信息 业务处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/user/profile")
public class SysProfileController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(username));
        ajax.put("postGroup", userService.selectUserPostGroup(username));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        user.setAvatar(null);
        user.setDeptId(null);
        if (userService.updateUserProfile(user) > 0) {
            // 更新缓存用户信息
            loginUser.getSysUser().setNickName(user.getNickName());
            loginUser.getSysUser().setPhonenumber(user.getPhonenumber());
            loginUser.getSysUser().setEmail(user.getEmail());
            loginUser.getSysUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getSysUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

//    /**
//     * 头像上传
//     */
//    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
//    @PostMapping("/avatar")
//    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file)
//    {
//        if (!file.isEmpty())
//        {
//            LoginUser loginUser = SecurityUtils.getLoginUser();
//            String extension = FileTypeUtils.getExtension(file);
//            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION))
//            {
//                return error("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
//            }
////            R<SysFile> fileResult = remoteFileService.upload(file);
//            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData()))
//            {
//                return error("文件服务异常，请联系管理员");
//            }
//            String url = fileResult.getData().getUrl();
//            if (userService.updateUserAvatar(loginUser.getUsername(), url))
//            {
//                AjaxResult ajax = AjaxResult.success();
//                ajax.put("imgUrl", url);
//                // 更新缓存用户头像
//                loginUser.getSysUser().setAvatar(url);
//                tokenService.setLoginUser(loginUser);
//                return ajax;
//            }
//        }
//        return error("上传图片异常，请联系管理员");
//    }
}
