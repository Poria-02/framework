package cn.upms.biz.mapper;

import cn.upms.api.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper 接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId 用户ID
     * @return 用户角色列表
     */
    List<SysRole> listRolesByUserId(Long userId);
}
