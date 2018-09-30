package org.schhx.toyspring.ioc;

import lombok.Data;

/**
 * @author shanchao
 * @date 2018-09-30
 */
@Data
public class BeanReference {

    private final String beanName;

    private Object source;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

}
