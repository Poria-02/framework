package cn.upms.biz.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


/**
 * (SysSensitiveLog)表实体类
 *
 * @author makejava
 * @since 2021-01-27 21:17:33
 */
@Data
public class SysSensitiveLog {


	@TableId(type = IdType.ASSIGN_ID)

	private String id;

	/**
	 * 创建人姓名
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建人姓名")
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private Date createTime;

	/**
	 * 是否删除0未删除，1-已删除
	 */
	@TableLogic(value = "0", delval = "1")
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "是否删除0未删除，1-已删除")
	private Integer isDelete;

	/**
	 * 敏感信息
	 */

	@Schema(description = "敏感信息")
	private String sensitiveInfo;

	/**
	 * 用户类型 1-用户，2-患者 3-医护
	 */

	@Schema(description = "用户类型 1-用户，2-患者 3-医护")
	private Integer userType;

	@Schema(description = "查看人姓名")
	private String createName;
}