package org.schhx.toyspring.ioc;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}
