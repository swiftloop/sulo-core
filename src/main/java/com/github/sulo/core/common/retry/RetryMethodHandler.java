package com.github.sulo.core.common.retry;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sorata 2020-11-25 16:14
 */
public class RetryMethodHandler implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryMethodHandler.class);


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        final Retry retry = methodInvocation.getMethod().getAnnotation(Retry.class);
        int count = retry.value();
        if (count < 0) {
            LOGGER.error("重试的次数不能小于0，方法将不再重试。{}", methodInvocation.getMethod().getDeclaringClass().getName()
                    + "#" + methodInvocation.getMethod().getName());
            count = 0;
        }
        Throwable exp;
        do {
            count--;
            try {
                return methodInvocation.proceed();
            } catch (Throwable throwable) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("方法执行失败，开始重试。{}", methodInvocation.getMethod().getDeclaringClass().getName()
                            + "#" + methodInvocation.getMethod().getName());
                }
                final Class<? extends Throwable>[] exclude = retry.exclude();
                for (Class<? extends Throwable> e : exclude) {
                    if (throwable.getClass() == e) {
                        throw throwable;
                    }
                }
                exp = throwable;
            }
        } while (count > 0);

        throw exp;
    }
}
