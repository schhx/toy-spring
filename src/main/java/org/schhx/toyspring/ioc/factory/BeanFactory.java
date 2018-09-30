package org.schhx.toyspring.ioc.factory;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;
}
