package com.xiaojun.rbac.beans.rsp.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

// 保证序列化json的时候,如果是null的对象,key也会消失
// @JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL) 已过时
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Response<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    /**
     * 私有构造器 ####################################################
     */
    private Response(int status) {
        this.status = status;
    }

    private Response(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Response(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private Response(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 静态方法 ####################################################
     */
    // 使之不在json序列化结果当中，因为Jackson把isSuccess()和setSuccess()默认解析为success的get和set方法，坑！！！
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> Response<T> success() {
        return new Response<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Response<T> success(String msg) {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> Response<T> success(String msg, T data) {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> Response<T> error() {
        return new Response<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }

    public static <T> Response<T> error(String errorMessage) {
        return new Response<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> Response<T> error(int errorCode, String errorMessage) {
        return new Response<T>(errorCode, errorMessage);
    }
}
