package cn.upms.biz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description =  "树形节点")
public class TreeNode {
	@Schema(description =  "当前节点id")
	protected Long id;

	@Schema(description =  "父节点id")
	protected Long parentId;

	@Schema(description =  "子节点列表")
	protected List<TreeNode> children = new ArrayList<>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
