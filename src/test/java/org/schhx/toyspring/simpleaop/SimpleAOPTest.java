package org.schhx.toyspring.simpleaop;

import org.junit.Test;
import org.schhx.toyspring.service.Hello;
import org.schhx.toyspring.service.HelloImpl;

import static org.junit.Assert.*;

public class SimpleAOPTest {
    @Test
    public void getProxy() throws Exception {
        Hello proxy = (Hello) SimpleAOP.getProxy(new HelloImpl(), new LogBeforeAdvice());
        proxy.sayHello();
    }

}