package com.yzp.cdp.pattern23.singleton_单例模式.懒汉模式的线程安全;

//code 6
public class SynchronizedSingleton {
    //定义实例
    private static SynchronizedSingleton instance;

    //私有构造方法
    private SynchronizedSingleton() {
    }

    //对外提供获取实例的静态方法，对该方法加锁
    public static synchronized SynchronizedSingleton getInstance() {
        //在对象被使用的时候才实例化
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}
