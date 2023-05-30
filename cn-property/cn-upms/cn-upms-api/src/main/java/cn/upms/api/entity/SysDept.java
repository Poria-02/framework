package cn.upms.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 部门管理
 */
@Data
@Schema(description ="部门")
@EqualsAndHashCode(callSuper = true)
public class SysDept extends Model<SysDept> {
	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.AUTO)
	@Schema(description =  "部门id")
	private Long deptId;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	@Schema(description =  "部门名称")
	private String name;

	/**
	 * 排序
	 */
	@NotNull(message = "排序值不能为空")
	@Schema(description =  "排序值")
	private Integer sort;

	/**
	 * 创建时间
	 */
	@Schema(description =  "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description =  "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 父级部门id
	 */
	@Schema(description =  "父级部门id")
	private Long parentId;

	/**
	 * 是否删除  1：已删除  0：正常
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private String delFlag;

	@Schema(description = "部门类型  1-机构，2-机构下属科室")
	private Integer type;

	@Schema(description = "详情ID")
	private String detailId;
}
