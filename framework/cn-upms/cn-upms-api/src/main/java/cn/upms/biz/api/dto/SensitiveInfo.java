package cn.upms.biz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SensitiveInfo {

	@Schema(description = "手机号")
	private String mobile;

	@Schema(description = "身份证号")
	private String idCard;

	@Schema(description = "用户类型 1-用户，2-患者 3-医护")
	private Integer userType;

	@Schema(description = "用户id")
	private String userId;

}