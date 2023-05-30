package cn.upms.biz.service;

import cn.common.core.utils.R;
import cn.upms.api.dto.UserDTO;
import cn.upms.api.dto.UserInfo;
import cn.upms.api.dto.UserPwdUpdateDTO;
import cn.upms.api.entity.SysUser;
import cn.upms.api.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo findUserInfo(SysUser sysUser);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     */
    IPage getUsersWithRolePage(Page page, UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUser sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto 用户信息
     * @return Boolean
     */
    R<Boolean> updateUserInfo(UserDTO userDto);

    R<Boolean> updateUserPwd(UserPwdUpdateDTO userDto);


    /**
     * 更新指定用户信息
     *
     * @param userDto 用户信息
     */
    Boolean updateUser(UserDTO userDto);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO selectUserVoById(Integer id);

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    List<SysUser> listAncestorUsers(String username);

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    Boolean saveUser(UserDTO userDto);

    /**
     * 绑定网易运行
     *
     * @param userId  用户ID
     * @param yxToken 云信Token
     */
    Boolean bindYXToken(Integer userId, String yxToken);
}
