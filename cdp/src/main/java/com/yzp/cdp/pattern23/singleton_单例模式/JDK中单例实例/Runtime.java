package com.yzp.cdp.pattern23.singleton_单例模式.JDK中单例实例;

public class Runtime {
    private static Runtime currentRuntime = new Runtime();

    public static Runtime getRuntime() {
        return currentRuntime;
    }

    private Runtime() {
    }
}
