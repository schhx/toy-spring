package org.schhx.toyspring;

import lombok.Data;
import org.schhx.toyspring.ioc.InitializingBean;
import org.schhx.toyspring.ioc.factory.BeanFactory;
import org.schhx.toyspring.ioc.factory.BeanFactoryAware;

/**
 * @author shanchao
 * @date 2018-09-30
 */
@Data
public class Wheel implements BeanFactoryAware, InitializingBean {

    private String brand;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        System.out.println("wheel set bean factory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("wheel after properties set");
    }
}
