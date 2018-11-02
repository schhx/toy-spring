package org.schhx.toyspring.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.schhx.toyspring.aop.Pointcut;
import org.schhx.toyspring.aop.PointcutAdvisor;

/**
 * @author shanchao
 * @date 2018-10-08
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    private Advice advice;

    public void setExpression(String expression) {
        pointcut.setExpression(expression);
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
