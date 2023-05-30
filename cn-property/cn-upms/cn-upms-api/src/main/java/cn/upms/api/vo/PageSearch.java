package cn.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class PageSearch {
    @NotNull
    @Schema(description =  "第N页")
    private Integer current = 1;
    @NotNull
    @Schema(description =  "每页N条")
    private Integer size = 10;
}
