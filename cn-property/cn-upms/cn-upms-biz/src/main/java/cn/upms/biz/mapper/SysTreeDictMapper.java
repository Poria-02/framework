package cn.upms.biz.mapper;

import cn.upms.api.entity.SysTreeDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.datascope.annotation.DataScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysTreeDict)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-03 09:24:47
 */
@Mapper
public interface SysTreeDictMapper extends BaseMapper<SysTreeDict> {


    List<SysTreeDict> selectDicts(String code, String name, Integer start, Integer limit, DataScope dataScope);

    Integer selectDictsCount(String code, String name, DataScope dataScope);

    SysTreeDict findById(@Param("id") String id, DataScope dataScope);

}