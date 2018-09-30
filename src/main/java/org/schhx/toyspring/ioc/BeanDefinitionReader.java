package org.schhx.toyspring.ioc;

import java.util.Map;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public interface BeanDefinitionReader {

    Map<String, BeanDefinition> loadBeanDefinitions(String location) throws Exception;
}
