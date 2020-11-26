package com.github.sulo.core.exception;

/**
 * @author sorata 2020-11-25 15:09
 */
public class BizException extends BaseException {
    public BizException(Integer code, String message) {
        super(code, message);
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BizException(Integer code, Throwable cause) {
        super(code, cause);
    }
}
