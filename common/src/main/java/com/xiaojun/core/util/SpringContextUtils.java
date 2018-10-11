package com.xiaojun.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author f00lish
 * @version 2018/5/23
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    private static BeanDefinitionRegistry beanDefinitionRegistry;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        beanDefinitionRegistry = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 注册bean
     * @param beanId 所注册bean的id
     * @param className bean的className，
     */
    public static void registerBean(String beanId,String className) throws Exception {
        if (beanDefinitionRegistry == null){
            throw new Exception("beanDefinitionRegistry is null");
        }
        // get the BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(className);
        // get the BeanDefinition
        BeanDefinition beanDefinition=beanDefinitionBuilder.getBeanDefinition();
        // register the bean
        beanDefinitionRegistry.registerBeanDefinition(beanId,beanDefinition);
    }

    /**
     * 移除bean
     * @param beanId bean的id
     */
    public static void unregisterBean(String beanId) throws Exception {
        if (beanDefinitionRegistry == null)
            throw new Exception("beanDefinitionRegistry is null");
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        if (getApplicationContext() != null)
            return getApplicationContext().getBean(name);
        return null;
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (getApplicationContext() != null)
            return getApplicationContext().getBean(clazz);
        return null;
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (getApplicationContext() != null)
            return getApplicationContext().getBean(name, clazz);
        return null;
    }
}
