package cn.upms.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


/**
 * (SysTreeDict)表实体类
 *
 * @author makejava
 * @since 2020-07-03 09:24:41
 */
@Data
public class SysTreeDict {

    /**
     *ID
     */     
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     *编码
     */     
   
    @Schema(description = "编码")    
    private String code;

    /**
     *名称
     */     
   
    @Schema(description = "名称")    
    private String name;

    /**
     *备注
     */     
   
    @Schema(description = "备注")    
    private String remark;

    /**
     *字典类型 0:系统字典 1:用户字典
     */     
   
    @Schema(description = "字典类型 0:系统字典 1:用户字典")    
    private Integer type;

    /**
     *是否为树形结构
     */     
   
    @Schema(description = "是否为树形结构")    
    private Integer isTree;

    /**
     *租户ID
     */     
   
    @Schema(description = "租户ID")    
    private Integer tenantId;

         
   
        
    private Integer deptId;

    /**
     *已删除
     */     
    @TableLogic(value = "0",delval = "1")
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "已删除")    
    private Integer isDelete;

    /**
     *创建人ID
     */     
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人ID")    
    private String createBy;

    /**
     *创建时间
     */     
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")    
    private Date createTime;

    /**
     *修改人
     */     
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改人")    
    private String updateBy;

    /**
     *修改时间
     */     
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改时间")    
    private Date updateTime;

}