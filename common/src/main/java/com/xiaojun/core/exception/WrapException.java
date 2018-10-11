package com.xiaojun.core.exception;

public class WrapException extends RuntimeException {

    private Object reponse;

    public WrapException(String message, Object reponse) {
        super(message);
        this.reponse = reponse;
    }

    public WrapException(String message, Throwable cause, Object reponse) {
        super(message, cause);
        this.reponse = reponse;
    }

    public Object getReponse() {
        return reponse;
    }
}
