package org.schhx.toyspring.ioc.xml;

import org.schhx.toyspring.ioc.BeanPostProcessor;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        System.out.println("BeanPostProcessor before: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        System.out.println("BeanPostProcessor after: " + beanName);
        return bean;
    }
}
