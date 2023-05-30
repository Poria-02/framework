package cn.upms.biz.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserPwdUpdateDTO {

	@Schema(description =  "用户名")
	private String username;


	@Schema(description =  "原密码")
	@NotBlank(message = "原密码不能为空")
	private String password;


	@Schema(description =  "新密码")
	@Pattern(regexp = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,20}$",
			message = "密码必须包含大小写字母、数字、特殊符号，且至少8位"
	)
	private String newpassword1;

}
