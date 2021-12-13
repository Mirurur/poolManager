package com.amateur.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author sun
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtil.applicationContext;
    }

    public static Object getBean(String name) {
        checkApplicationContext();
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return getApplicationContext().getBean(clazz);
    }

    public static <T extends Annotation> Map<String, Object> getBeansWithAnnotation(Class<T> annotationType) {
        checkApplicationContext();
        return getApplicationContext().getBeansWithAnnotation(annotationType);
    }

    private static void checkApplicationContext() {
        Assert.notNull(applicationContext, "applicationContext未注入");
    }
}
