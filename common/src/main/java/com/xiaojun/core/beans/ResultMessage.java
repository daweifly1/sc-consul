package com.xiaojun.core.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("返回信息")
public class ResultMessage<T> {

    private static String SUCCESS = "操作成功";

    private static String ERROR = "操作失败";

    public static int OK = 0;

    public static int FAILL = 1;

    @ApiModelProperty(value = "提示信息", name = "message", example = "操作成功")
    protected String message;

    @ApiModelProperty(value = "数据", name = "data")
    protected T data;

    @ApiModelProperty(value = "状态码", name = "status")
    protected int status;

    @ApiModelProperty(value = "时间戳", name = "timestamp")
    private Long timestamp;

    public static <T> ResultMessage<T> success() {
        return success(ResultMessage.OK, SUCCESS, null);
    }


    public static <T> ResultMessage<T> success(T result) {
        return success(ResultMessage.OK, SUCCESS, result);
    }

    public static <T> ResultMessage<T> success(String message, T result) {
        return success(ResultMessage.OK, message, result);
    }

    public static <T> ResultMessage<T> success(int status, String message) {
        return success(status, message, null);
    }

    public static <T> ResultMessage<T> success(int status, String message, T result) {
        return new ResultMessage<T>()
                .data(result)
                .message(message)
                .putTimeStamp()
                .status(status);
    }

    private ResultMessage<T> message(String message) {
        this.message = message;
        return this;
    }

    public static <T> ResultMessage<T> error() {

        return error(FAILL, ERROR, null);
    }

    public static <T> ResultMessage<T> error(String message) {

        return error(FAILL, message, null);
    }

    public static <T> ResultMessage<T> error(int status, String message) {

        return error(status, message, null);
    }

    public static <T> ResultMessage<T> error(int status, String message, T result) {
        ResultMessage<T> responseMessage = new ResultMessage<>();
        responseMessage.message = message;
        responseMessage.status(status);
        responseMessage.data(result);
        return responseMessage.putTimeStamp();
    }

    public ResultMessage<T> data(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return this.data;
    }

    private ResultMessage<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResultMessage<T> status(int status) {
        this.status = status;
        return this;
    }
}
