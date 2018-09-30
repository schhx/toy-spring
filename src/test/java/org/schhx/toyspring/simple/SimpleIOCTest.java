package org.schhx.toyspring.simple;

import org.junit.Test;
import org.schhx.toyspring.Car;
import org.schhx.toyspring.Wheel;

import static org.junit.Assert.*;


public class SimpleIOCTest {
    @Test
    public void getBean() throws Exception {
        String location = SimpleIOC.class.getClassLoader().getResource("toy-spring-ioc.xml").getFile();
        SimpleIOC bf = new SimpleIOC(location);

        Car car = (Car) bf.getBean("car");
        assertNotEquals(null, car);
        System.out.println(car);

        Wheel wheel = (Wheel) bf.getBean("wheel");
        assertNotEquals(null, wheel);
        System.out.println(wheel);
    }

}