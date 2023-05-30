package cn.upms.biz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门树
 */
@Data
@Schema(description =  "部门树")
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {
	@Schema(description =  "部门名称")
	private String name;
}
