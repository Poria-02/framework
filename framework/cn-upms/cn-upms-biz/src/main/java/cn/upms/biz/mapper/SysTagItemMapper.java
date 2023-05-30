package cn.upms.biz.mapper;

import cn.upms.biz.api.entity.SysTagItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签项(SysTagItem)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-27 14:35:06
 */
@Mapper
public interface SysTagItemMapper extends BaseMapper<SysTagItem> {

}