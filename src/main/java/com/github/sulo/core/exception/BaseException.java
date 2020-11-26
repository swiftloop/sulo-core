package com.github.sulo.core.exception;

/**
 * @author sorata 2020-11-25 15:04
 */
public class BaseException extends RuntimeException {

    private final Integer code;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
