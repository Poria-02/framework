package cn.upms.biz.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 字典表
 */
@Data
@Schema(description = "字典类型")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends Model<SysDict> {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	@Schema(description =  "字典编号")
	private Integer id;

	/**
	 * 类型
	 */
	@Schema(description =  "字典类型")
	private String type;

	/**
	 * 描述
	 */
	@Schema(description =  "字典描述")
	private String description;

	/**
	 * 创建时间
	 */
	@Schema(description =  "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description =  "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 是否是系统内置
	 */
	@TableField(value = "`system`")
	@Schema(description =  "是否系统内置")
	private String system;

	/**
	 * 备注信息
	 */
	@Schema(description = "备注信息")
	private String remarks;

	/**
	 * 删除标记
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private String delFlag;
}
