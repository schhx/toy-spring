package org.schhx.toyspring.aop;

import java.lang.reflect.Method;

/**
 * @author shanchao
 * @date 2018-10-08
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);

}
