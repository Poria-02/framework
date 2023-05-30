package cn.upms.biz.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TreeDictModel {



	@NotBlank(message = "id不能为空")
	@Schema(description =  "字典ID")
	private String id;

	@NotBlank(message = "名称不能为空")
	@Schema(description =  "名称")
	private String name;

	@Schema(description =  "备注")
	private String remark = "";

}
