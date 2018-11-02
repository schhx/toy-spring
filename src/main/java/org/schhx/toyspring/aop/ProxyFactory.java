package org.schhx.toyspring.aop;

/**
 * @author shanchao
 * @date 2018-11-02
 */
public class ProxyFactory extends AdvisedSupport {

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        return new JdkDynamicAopProxy(this);
    }
}
