package cn.upms.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


/**
 * (SysTreeDictItem)表实体类
 *
 * @author makejava
 * @since 2020-07-03 09:24:56
 */
@Data
public class SysTreeDictItem {


	/**
	 *ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "ID")
	private String id;

	/**
	 *字典ID
	 */

	@Schema(description = "字典ID")
	private String dictId;

	/**
	 *名称
	 */

	@Schema(description = "名称")
	private String name;

	/**
	 *简称
	 */

	@Schema(description = "简称")
	private String simpleName;

	/**
	 *备注
	 */

	@Schema(description = "备注")
	private String remark;

	/**
	 *值
	 */

	@Schema(description = "值")
	private String value;

	/**
	 *扩展字段
	 */

	@Schema(description = "扩展字段")
	private String ext1;

	@Schema(description = "排序,最长6位")
	private Integer sort;

	/**
	 *上一级ID
	 */

	@Schema(description = "上一级ID")
	private String pid;

	/**
	 *已删除 0:否 1:是
	 */
	@TableLogic(value = "0",delval = "1")
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "已删除 0:否 1:是")
	private Integer isDelete;

	/**
	 *创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = " 创建时间")
	private Date createTime;

	/**
	 *修改人
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "修改人")
	private String updateBy;

	/**
	 *修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "修改时间")
	private Date updateTime;
}