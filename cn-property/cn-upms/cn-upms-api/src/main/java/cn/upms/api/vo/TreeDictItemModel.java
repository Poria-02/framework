package cn.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TreeDictItemModel {
    private String id;
    @NotNull
    private String name;
    private String simpleName="";
    private String remark="";
    private String value="";
    private String ext1="";
    private String pid="";

	@Max(value = 999999,message = "排序值过大")
	@Min(value = 0,message = "排序值太小")
    @Schema(description = "排序,最长6位")
	private Integer sort;


    private List<TreeDictItemModel> childs;

    @NotEmpty
    private String dictId;
}
