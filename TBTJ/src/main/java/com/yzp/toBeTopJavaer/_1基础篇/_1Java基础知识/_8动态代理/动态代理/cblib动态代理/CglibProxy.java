package com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.动态代理.cblib动态代理;

import com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._8动态代理.动态代理.UserServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        //设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    //实现MethodInterceptor接口方法
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("前置代理");
        //通过代理类调用父类中的方法
        Object result = methodProxy.invokeSuper(o,objects);
        System.out.println("后置代理");
        return result;
    }

}
 class DoCGLib{
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        //通过生成子类的方式创建代理类
        UserServiceImpl proxyImp= (UserServiceImpl) proxy.getProxy(UserServiceImpl.class);
        proxyImp.add();
    }
}