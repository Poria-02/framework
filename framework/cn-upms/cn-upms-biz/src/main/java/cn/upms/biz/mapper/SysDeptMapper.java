package cn.upms.biz.mapper;

import cn.upms.biz.api.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.shanxincd.plat.common.data.datascope.DataScope;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门管理 Mapper 接口
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
	/**
	 * 根据数据权限查询部门
	 */
	List<SysDept> listDepts(DataScope dataScope);
}
