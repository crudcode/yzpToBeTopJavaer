package com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.动态代理.jdk动态代理;


import com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.动态代理.UserService;
import com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.动态代理.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("-----------------begin "+method.getName()+"--------------------");
        Object result = method.invoke(target, args);
        System.out.println("-------------------end " + method.getName() + "-----------------------");

        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }
}

class test {

    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        MyInvocationHandler handler= new MyInvocationHandler(service);
        UserService proxy= (UserService) handler.getProxy();
        proxy.add();
    }
}

