package cn.upms.biz.api.entity;

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
 * 角色表
 */
@Data
@Schema(description = "角色")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends Model<SysRole> {
	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	@Schema(description =  "角色编号")
	private Long roleId;

	@NotBlank(message = "角色名称不能为空")
	@Schema(description =  "角色名称")
	private String roleName;

	@NotBlank(message = "角色标识不能为空")
	@Schema(description =  "角色标识")
	private String roleCode;

	@Schema(description =  "角色描述")
	private String roleDesc;

	@NotNull(message = "数据权限类型不能为空")
	@Schema(description =  "数据权限类型")
	private Integer dsType;

	/**
	 * 数据权限作用范围
	 */
	@Schema(description =  "数据权限作用范围")
	private String dsScope;

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
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private String delFlag;
}
