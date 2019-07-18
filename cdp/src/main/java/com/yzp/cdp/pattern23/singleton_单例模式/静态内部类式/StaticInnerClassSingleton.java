package com.yzp.cdp.pattern23.singleton_单例模式.静态内部类式;

//code 4
public class StaticInnerClassSingleton {
    //在静态内部类中初始化实例对象
    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSANCE = new StaticInnerClassSingleton();
    }

    //私有的构造方法
    private StaticInnerClassSingleton() {
    }

    //对外提供获取实例的静态方法
    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSANCE;
    }
}
