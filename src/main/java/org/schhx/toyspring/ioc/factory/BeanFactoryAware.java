package org.schhx.toyspring.ioc.factory;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
