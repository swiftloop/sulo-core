package com.github.sulo.core.common.retry;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author sorata 2020-11-25 17:20
 */
public class RetryStaticPointcout extends StaticMethodMatcherPointcutAdvisor {

    public RetryStaticPointcout(Advice advice) {
        setAdvice(advice);
    }

    public RetryStaticPointcout(Advice advice, int order) {
        setAdvice(advice);
        setOrder(order);
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getAnnotation(Retry.class) != null;
    }
}
