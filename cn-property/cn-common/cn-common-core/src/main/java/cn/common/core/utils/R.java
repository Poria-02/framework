package cn.common.core.utils;

import cn.common.core.constant.CommonConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@ApiModel(value = "响应信息主体")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "返回标记：成功标记=200，失败标记=500")
    private int code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "数据")
    private T data;

    @JsonIgnore
    public boolean isSuccess() {
        return CommonConstants.SUCCESS.equals(this.getCode());
    }

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> ok(T data, boolean detail) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg, boolean detail) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


}
