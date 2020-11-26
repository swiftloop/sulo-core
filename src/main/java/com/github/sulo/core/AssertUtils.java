package com.github.sulo.core;


import com.github.sulo.core.exception.BizException;

/**
 * @author sorata 2020-11-25 15:17
 */
public abstract class AssertUtils {


    public static void isTrue(boolean expression, Integer code, String message) {
        if (!expression) {
            throw new BizException(code, message);
        }
    }


    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BizException(500, message);
        }
    }


    public static void nonNull(Object o, Integer code, String message) {
        if (o == null) {
            throw new BizException(code, message);
        }
    }


    public static void nonNull(Integer code, String message, Object... objects) {
        if (objects == null) {
            throw new BizException(code, message);
        }
        for (Object o : objects) {
            nonNull(o, code, message);
        }
    }


}
