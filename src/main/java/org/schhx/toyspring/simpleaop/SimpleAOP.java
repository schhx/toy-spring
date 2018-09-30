package org.schhx.toyspring.simpleaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class SimpleAOP {

    public static Object getProxy(Object target, Advice advice) {

        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(advice instanceof BeforeAdvice) {
                    BeforeAdvice beforeAdvice = (BeforeAdvice)advice;
                    beforeAdvice.before(proxy, method, args);
                }
                return method.invoke(target, args);
            }
        };

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler);
    }


}
