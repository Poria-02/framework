package cn.common.core.utils;

import cn.common.core.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author ruoyi
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

    public static <T> R<T> ok() {
        return restResult(null, Constants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, Constants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, Constants.SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, Constants.FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, Constants.FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, Constants.FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, Constants.FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return Constants.SUCCESS == ret.getCode();
    }
}
