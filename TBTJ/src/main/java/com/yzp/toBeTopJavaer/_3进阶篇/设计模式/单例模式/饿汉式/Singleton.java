package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.饿汉式;

public class Singleton {
    //在类内部实例化一个实例
    private static Singleton instance = new Singleton();

    //私有的构造函数，外部无法访问
    private Singleton() {
    }

    //对外提供获取实例的静态方法
    public static Singleton getInstance() {
        return instance;
    }
}
