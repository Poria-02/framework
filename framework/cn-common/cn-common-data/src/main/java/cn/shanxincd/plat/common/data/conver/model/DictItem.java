package cn.shanxincd.plat.common.data.conver.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DictItem {

	/**
	 * 数据值
	 */
	@Schema(description =  "数据值")
	private String value;
	/**
	 * 标签名
	 */
	@Schema(description = "标签名")
	private String label;

}
