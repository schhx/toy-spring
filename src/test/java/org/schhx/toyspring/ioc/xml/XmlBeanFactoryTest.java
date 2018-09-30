package org.schhx.toyspring.ioc.xml;

import org.junit.Test;
import org.schhx.toyspring.Car;
import org.schhx.toyspring.Wheel;
import org.schhx.toyspring.ioc.factory.BeanFactory;

import static org.junit.Assert.assertNotEquals;

public class XmlBeanFactoryTest {
    @Test
    public void getBean() throws Exception {
        String location = this.getClass().getClassLoader().getResource("spring-ioc.xml").getFile();
        BeanFactory bf = new XmlBeanFactory(location);

        Car car = (Car) bf.getBean("car");
        assertNotEquals(null, car);
        System.out.println(car);

        Wheel wheel = (Wheel) bf.getBean("wheel");
        assertNotEquals(null, wheel);
        System.out.println(wheel);
    }

}