package org.schhx.toyspring.simpleaop;

import lombok.Setter;

import java.lang.reflect.Method;

/**
 * @author shanchao
 * @date 2018-09-30
 */
@Setter
public abstract class BeforeAdvice implements Advice {

    /**
     * 前置处理
     * @param proxy
     * @param method
     * @param args
     * @throws Throwable
     */
    protected abstract void before(Object proxy, Method method, Object[] args) throws Throwable;
}
