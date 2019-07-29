package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.静态内部类式;

public class SingletonClient {
    public static void main(String[] args) {
        StaticInnerClassSingleton instance = StaticInnerClassSingleton.getInstance();
        StaticInnerClassSingleton instance1 = StaticInnerClassSingleton.getInstance();
        System.out.println(instance == instance1);
    }
}
