package cn.shanxincd.plat.common.data.conver.dao;

import cn.shanxincd.plat.common.data.conver.model.ConverResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface ConverDao extends BaseMapper {

	@Select({"<script>",
			" select ${keyField} as 'key' , ${valueField} as 'value' from ${table} where ${keyField} in ",
			" <foreach collection='keys' item='key' separator=',' open='(' close=')'> ",
			" #{key} ",
			" </foreach>",
			"</script>"})
	List<ConverResult> convertData(@Param("keys") Set<String> keys,
                                 @Param("table") String table,
                                 @Param("keyField") String keyField,
                                 @Param("valueField") String valueField);
}
