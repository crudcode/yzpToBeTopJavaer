package com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.静态代理;

public class Main {

    public void testProxy() {
        //目标对象
        HelloService target = new HelloServiceImpl();
        //代理对象
        HelloServiceProxy proxy = new HelloServiceProxy(target);
        proxy.say();
    }
}
