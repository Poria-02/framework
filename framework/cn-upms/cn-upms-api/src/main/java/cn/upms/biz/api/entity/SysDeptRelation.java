package cn.upms.biz.api.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门关系表
 */
@Data
@Schema(description = "部门关系")
@EqualsAndHashCode(callSuper = true)
public class SysDeptRelation extends Model<SysDeptRelation> {
	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	@Schema(description =  "祖先节点")
	private Long ancestor;

	/**
	 * 后代节点
	 */
	@Schema(description =  "后代节点")
	private Long descendant;
}
