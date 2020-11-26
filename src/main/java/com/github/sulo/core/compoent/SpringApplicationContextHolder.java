package com.github.sulo.core.compoent;

import com.github.sulo.core.exception.SysException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author sorata 2020-11-25 15:37
 */
@Component
public class SpringApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContextHolder.applicationContext = applicationContext;
    }



    public static ApplicationContext getContext(){
        return applicationContext;
    }


    public static <T> T getBean(Class<T> tClass){
        try {
            return applicationContext.getBean(tClass);
        } catch (Exception e) {
            //ignore
        }
        throw new SysException(9999,"Bean "+ tClass + " 未找到，请确认是否被扫描到");
    }

    public static Object getBean(String beanName){
        try {
            return applicationContext.getBean(beanName);
        } catch (Exception e) {
            //ignore
        }
        throw new SysException(9999,"Bean "+ beanName + " 未找到，请确认是否被扫描到");
    }



}
