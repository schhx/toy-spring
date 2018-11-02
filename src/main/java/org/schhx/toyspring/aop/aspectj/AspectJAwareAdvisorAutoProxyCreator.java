package org.schhx.toyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.schhx.toyspring.aop.ProxyFactory;
import org.schhx.toyspring.aop.TargetSource;
import org.schhx.toyspring.ioc.BeanPostProcessor;
import org.schhx.toyspring.ioc.factory.BeanFactory;
import org.schhx.toyspring.ioc.factory.BeanFactoryAware;
import org.schhx.toyspring.ioc.xml.XmlBeanFactory;

import java.util.List;

/**
 * @author shanchao
 * @date 2018-11-02
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    private XmlBeanFactory xmlBeanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        if (shouldSkip(bean)) {
            return bean;
        }

        // 1.  从 BeanFactory 查找 AspectJExpressionPointcutAdvisor 类型的对象
        List<AspectJExpressionPointcutAdvisor> advisors =
                xmlBeanFactory.getBeansByType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {

            // 2. 使用 Pointcut 对象匹配当前 bean 对象
            if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                ProxyFactory proxyFactory = new ProxyFactory();
                proxyFactory.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
                proxyFactory.setTargetSource(targetSource);

                // 3. 生成代理对象，并返回
                Object proxy = proxyFactory.getProxy();
                return proxy;
            }
        }

        // 2. 匹配失败，返回 bean
        return bean;
    }

    private boolean shouldSkip(Object bean) {
        /* 这里两个 if 判断很有必要，如果删除将会使程序进入死循环状态，
         * 最终导致 StackOverflowError 错误发生
         */
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return true;
        }
        if (bean instanceof MethodInterceptor) {
            return true;
        }
        return false;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        xmlBeanFactory = (XmlBeanFactory) beanFactory;
    }
}
