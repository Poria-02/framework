package cn.upms.biz.mapper;

import cn.upms.api.entity.SysDeptRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 */
@Mapper
public interface SysDeptRelationMapper extends BaseMapper<SysDeptRelation> {
    /**
     * 删除部门关系表数据
     *
     * @param id 部门ID
     */
    void deleteDeptRelationsById(Long id);

    /**
     * 更改部分关系表数据
     */
    void updateDeptRelations(SysDeptRelation deptRelation);
}
