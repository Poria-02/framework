package cn.upms.biz.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



/**
 * 版权所有 泰山信息技术有限公司
 *
 * @Author: yuhaitao
 * @Date 2020/5/10 6:01 下午
 */
@Data
public class PageSearch {
    @NotNull
    @Schema(description =  "第N页")
    private Integer current = 1;
    @NotNull
    @Schema(description =  "每页N条")
    private Integer size = 10;
}
