package cn.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 秦
 * @date 2023/1/10
 * @description
 */
@Data
public class PageSearch {
	@NotNull
	@ApiModelProperty(value = "第N页")
	private Integer current = 1;
	@NotNull
	@ApiModelProperty(value = "每页N条")
	private Integer size = 10;
}
