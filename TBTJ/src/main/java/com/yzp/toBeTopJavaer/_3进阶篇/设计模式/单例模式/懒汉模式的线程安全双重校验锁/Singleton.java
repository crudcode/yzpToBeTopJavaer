package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.懒汉模式的线程安全双重校验锁;

//code 7
public class Singleton {
    private static Singleton singleton;

    private Singleton() {
    }
    public static Singleton getSingleton(){
        if(singleton == null)
        {
            synchronized (Singleton.class)
            {
                if(singleton == null)
                {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
