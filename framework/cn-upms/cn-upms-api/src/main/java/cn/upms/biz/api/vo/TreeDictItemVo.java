package cn.upms.biz.api.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有 泰山信息技术有限公司
 *
 * @Author: yuhaitao
 * @Date 2020/5/10 9:35 下午
 */
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
