package org.schhx.toyspring.simpleaop;

import java.lang.reflect.Method;

/**
 * @author shanchao
 * @date 2018-09-30
 */
public class LogBeforeAdvice extends BeforeAdvice {
    @Override
    protected void before(Object proxy, Method method, Object[] args) throws Throwable{
        System.out.println("log before advice");
    }
}
