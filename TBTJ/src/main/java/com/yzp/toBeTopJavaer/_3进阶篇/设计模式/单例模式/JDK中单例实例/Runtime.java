package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.JDK中单例实例;

public class Runtime {
    private static Runtime currentRuntime = new Runtime();

    public static Runtime getRuntime() {
        return currentRuntime;
    }

    private Runtime() {
    }
}
