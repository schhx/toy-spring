package org.schhx.toyspring.ioc;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author shanchao
 * @date 2018-09-30
 */
@Data
@Accessors(chain = true)
public class BeanDefinition {

    private Object bean;

    private String beanName;

    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition setBeanClass(String className) {
        try {
            this.beanClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}
