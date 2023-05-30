package cn.upms.api.dto;

import cn.upms.api.vo.MenuVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Schema(description = "菜单树")
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode implements Serializable {
    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;
    private boolean spread = false;

    /**
     * 前端路由标识路径
     */
    @Schema(description = "前端路由标识路径")
    private String path;

    /**
     * 路由缓冲
     */
    @Schema(description = "路由缓冲")
    private String keepAlive;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    private String permission;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @Schema(description = "菜单类型,0:菜单 1:按钮")
    private String type;

    /**
     * 菜单标签
     */
    @Schema(description = "菜单标签")
    private String label;

    /**
     * 排序值
     */
    @Schema(description = "排序值")
    private Integer sort;

    /**
     * 是否包含子节点
     */
    private Boolean hasChildren;

    public MenuTree() {
    }

    public MenuTree(long id, String name, long parentId) {
        this.id = id;
        this.name = name;
        this.label = name;
        this.parentId = parentId;
    }

    public MenuTree(long id, String name, MenuTree parent) {
        this.id = id;
        this.name = name;
        this.label = name;
        this.parentId = parent.getId();
    }

    public MenuTree(MenuVO menuVo) {
        this.id = menuVo.getMenuId();
        this.parentId = menuVo.getParentId();
        this.icon = menuVo.getIcon();
        this.name = menuVo.getName();
        this.path = menuVo.getPath();
        this.type = menuVo.getType();
        this.permission = menuVo.getPermission();
        this.label = menuVo.getName();
        this.sort = menuVo.getSort();
        this.keepAlive = menuVo.getKeepAlive();
    }
}
