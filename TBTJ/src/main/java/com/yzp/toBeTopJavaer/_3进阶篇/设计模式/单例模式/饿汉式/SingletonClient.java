package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.饿汉式;

public class SingletonClient {

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton == singleton1);//输出结果true

    }
}
