package org.schhx.toyspring.aop;


import org.aopalliance.aop.Advice;

/**
 * @author shanchao
 * @date 2018-10-08
 */
public interface Advisor {

    Advice getAdvice();

}
