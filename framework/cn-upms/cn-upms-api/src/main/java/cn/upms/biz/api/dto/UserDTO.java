package cn.upms.biz.api.dto;

import cn.upms.biz.api.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Schema(description =  "系统用户传输对象")
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */
	@Schema(description =  "角色id集合")
	private List<Long> role;

	/**
	 * 部门id
	 */
	@Schema(description =  "部门id")
	private Long deptId;

	/**
	 * 新密码
	 */
	@Schema(description =  "新密码")
	@Pattern(regexp = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,20}$",
			message = "密码必须包含大小写字母、数字、特殊符号，且至少8位"
	)
	private String newpassword1;
}
