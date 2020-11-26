package com.github.sulo.core.exception;

/**
 * @author sorata 2020-11-25 15:06
 */
public class SysException extends BaseException {
    public SysException(Integer code, String message) {
        super(code, message);
    }

    public SysException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public SysException(Integer code, Throwable cause) {
        super(code, cause);
    }
}
