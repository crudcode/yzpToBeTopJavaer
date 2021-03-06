package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.懒汉模式的线程安全双重校验锁优化;

//code 8
public class VolatileSingleton {
    private static volatile VolatileSingleton singleton;

    private VolatileSingleton() {
    }

    public static VolatileSingleton getSingleton() {
        if (singleton == null) {
            synchronized (VolatileSingleton.class) {
                if (singleton == null) {
                    singleton = new VolatileSingleton();
                }
            }
        }
        return singleton;
    }
}
