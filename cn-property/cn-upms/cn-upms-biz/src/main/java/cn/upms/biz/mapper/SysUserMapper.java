package cn.upms.biz.mapper;

import cn.upms.api.dto.UserDTO;
import cn.upms.api.entity.SysUser;
import cn.upms.api.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.datascope.annotation.DataScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO getUserVoByUsername(String username);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param page      分页
     * @param userDTO   查询参数
     * @param dataScope 数据范围
     * @return list
     */
    IPage<List<UserVO>> getUserVosPage(Page page, @Param("query") UserDTO userDTO, DataScope dataScope);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return userVo
     */
    UserVO getUserVoById(Integer id);
}
