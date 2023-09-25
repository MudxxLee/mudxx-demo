package com.mudxx.demo.boot.redis.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author laiw
 * @date 2023/7/25
 */
public class SpringContextStaticUtils {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextStaticUtils.applicationContext == null) {
            SpringContextStaticUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static Object getBean(String beanName) {
        checkApplicationContext();
        return applicationContext.getBean(beanName);
    }


    /**
     * 通过class获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }


    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext未注入");
        }
    }

}