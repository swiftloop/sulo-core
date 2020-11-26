package com.github.sulo.core.common.retry;

import java.lang.annotation.*;

/**
 * @author sorata 2020-11-25 16:09
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retry {

    int value() default 3;

    Class<? extends Throwable>[] exclude() default {};

}
