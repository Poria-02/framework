package cn.upms.biz.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TreeDictVO {

	@Schema(description =  "字典ID")
	private String id;

	@NotEmpty
	@Schema(description =  "字典Code")
	private String code;

	@NotEmpty
	@Schema(description =  "名称")
	private String name;

	@Schema(description =  "备注")
	private String remark = "";

	@Schema(description =  "类型 0：系统字典 1：业务字典")
	private int type;

	@NotNull
	@Schema(description =  "字典内容类型 0：列表 1：树")
	private Boolean isTree;

	@Schema(description =  "类型名称")
	private String typeName;

}
