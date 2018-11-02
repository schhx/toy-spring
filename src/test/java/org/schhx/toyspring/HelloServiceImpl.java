package org.schhx.toyspring;

/**
 * @author shanchao
 * @date 2018-11-02
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHelloWorld() {
        System.out.println("hello world!");
    }
}
