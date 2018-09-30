package org.schhx.toyspring.ioc.xml;

import org.schhx.toyspring.ioc.BeanPostProcessor;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class MyBeanPostProcessor2 implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        System.out.println("BeanPostProcessor 2 before: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        System.out.println("BeanPostProcessor 2 after: " + beanName);
        return bean;
    }
}
