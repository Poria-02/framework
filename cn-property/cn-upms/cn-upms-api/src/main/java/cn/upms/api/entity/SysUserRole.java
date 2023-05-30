package cn.upms.api.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色表
 */
@Data
@Schema(description = "用户角色")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Schema(description =  "用户id")
	private Long userId;

	/**
	 * 角色ID
	 */
	@Schema(description =  "角色id")
	private Long roleId;
}
