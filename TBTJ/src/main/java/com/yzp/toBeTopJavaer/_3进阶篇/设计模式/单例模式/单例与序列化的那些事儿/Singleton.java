package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.单例与序列化的那些事儿;

import java.io.Serializable;

public class Singleton implements Serializable {
    private volatile static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
