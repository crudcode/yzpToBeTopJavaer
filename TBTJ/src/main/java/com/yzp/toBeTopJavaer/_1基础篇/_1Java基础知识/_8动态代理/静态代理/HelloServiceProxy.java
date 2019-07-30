package com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.静态代理;

public class HelloServiceProxy implements HelloService {

    private HelloService target;

    public HelloServiceProxy(HelloService target) {
        this.target = target;
    }

    @Override
    public void say() {
        System.out.println("记录日志");
        target.say();
        System.out.println("清理数据");

    }
}
