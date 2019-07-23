package com.yzp.cdp.pattern23.singleton_单例模式.单例与序列化的那些事儿;

public class Singleton防止破坏单例 {
    private volatile static Singleton防止破坏单例 singleton;

    public Singleton防止破坏单例() {
    }

    public static Singleton防止破坏单例 getInstance() {
        if (singleton == null) {
            synchronized (Singleton防止破坏单例.class) {
                if (singleton == null) {
                    singleton = new Singleton防止破坏单例();
                }
            }
        }
        return singleton;
    }

    private Object readResolve() {
        return singleton;
    }
}
