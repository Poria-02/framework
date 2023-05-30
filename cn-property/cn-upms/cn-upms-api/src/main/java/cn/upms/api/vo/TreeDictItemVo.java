package cn.upms.api.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class TreeDictItemVo implements Serializable {
    private String id;
    @NotNull
    private String name;
    private String simpleName="";
    private String remark="";
    private String value="";
    private String ext1="";
    private String pid="";
	private Integer sort;
    private List<TreeDictItemVo> childs;

    @NotEmpty
    private String dictId;
}
