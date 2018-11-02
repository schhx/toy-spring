package org.schhx.toyspring.aop;

import org.junit.Test;
import org.schhx.toyspring.HelloService;
import org.schhx.toyspring.ioc.factory.BeanFactory;
import org.schhx.toyspring.ioc.xml.XmlBeanFactory;

/**
 * @author shanchao
 * @date 2018-11-02
 */
public class AopTest {

    @Test
    public void testAOP() throws Exception {
        String location = this.getClass().getClassLoader().getResource("spring-aop.xml").getFile();
        BeanFactory bf = new XmlBeanFactory(location);

        HelloService helloService = (HelloService) bf.getBean("helloService");
        helloService.sayHelloWorld();
    }
}
