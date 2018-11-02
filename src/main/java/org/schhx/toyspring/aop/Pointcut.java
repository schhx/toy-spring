package org.schhx.toyspring.aop;

/**
 * @author shanchao
 * @date 2018-10-08
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
