package cn.upms.biz.api.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 标签分类(SysTag)表实体类
 *
 * @author makejava
 * @since 2020-08-27 14:34:57
 */
@Data
public class SysTag extends Model<SysTag> {

    /**
     *id
     */     
    @TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "id")
    private String id;

    /**
     *标签key(唯一)
     */

	@Schema(description = "标签key(唯一)")
    private String tagKey;

    /**
     *标签分类名称
     */

	@Schema(description = "标签分类名称")
    private String name;

    /**
     *创建时间
     */     
    @TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
    private Date createTime;

    /**
     *创建人
     */     
    @TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建人")
    private String createBy;

    /**
     *更新时间
     */     
    @TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新时间")
    private Date updateTime;

    /**
     *更新人
     */     
    @TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新人")
    private String updateBy;

    /**
     *是否删除0未删除，1-已删除
     */     
    @TableLogic(value = "0",delval = "1")
    @TableField(fill = FieldFill.INSERT)
	@Schema(description = "是否删除0未删除，1-已删除")
    private Integer isDelete;
}