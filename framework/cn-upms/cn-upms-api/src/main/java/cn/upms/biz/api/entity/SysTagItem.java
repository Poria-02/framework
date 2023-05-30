package cn.upms.biz.api.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 标签项(SysTagItem)表实体类
 *
 * @author makejava
 * @since 2020-08-27 14:35:06
 */
@Data
public class SysTagItem extends Model<SysTagItem> {

    /**
     *id
     */     
    @TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "id")    
    private String id;

	@Schema(description = "标签分类id")
    private String tagId;

    /**
     *标签名称
     */     
   
    @Schema(description = "标签名称")    
    private String name;

    /**
     *标签值
     */     
   
    @Schema(description = "标签值")    
    private String value;

    /**
     *标签icon地址
     */     
   
    @Schema(description = "标签icon地址")    
    private String icon;

    /**
     *标签颜色
     */     
   
    @Schema(description = "标签颜色")    
    private String color;

    /**
     *状态1-启用，2-停用
     */     
   
    @Schema(description = "状态1-启用，2-停用")    
    private Integer status;

    /**
     *备注
     */     
   
    @Schema(description = "备注")    
    private String remark;

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