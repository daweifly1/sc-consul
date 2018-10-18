package com.xiaojun.rbac.beans.rsp.exception;

import lombok.Getter;

/**
 * Author: Mr.X
 * Date: 2018/1/21 下午1:59
 * Description:
 */
@Getter
public class EasException extends RuntimeException {

    private Integer code;

    public EasException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
