package org.schhx.toyspring.ioc;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
