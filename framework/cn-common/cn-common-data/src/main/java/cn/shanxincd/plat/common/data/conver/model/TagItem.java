package cn.shanxincd.plat.common.data.conver.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TagItem {

	@Schema(description = "标签值")
	private String value;

	@Schema(description = "标签名")
	private String name;

	@Schema(description = "标签icon地址")
	private String icon;


	@Schema(description = "标签颜色")
	private String color;

	@Schema(description = "备注")
	private String remark;

}
