## 不使用synchronized和lock，如何实现一个线程安全的单例？
枚举
```java
public enum Singleton{
    INSTANCE;
    public void whateverMethod(){}
}

```
静态内部类
```java
public class Singleton{
    private static class SingletonHolder{
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton(){}
    public static final Singleton getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
```
还有饿汉
```java
public class Singleton{
    private static Singleton instance = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){
        return instance;
    }
}
```
饿汉变种
```java
public class Singleton{
    private Singleton instance = null;
    static {
        instance = new Singleton();
    }
    private Singleton(){}
    public static Singleton getInstance()
    {
        return this.instance;
    }
}

```
>问：这几种实现单例的方式的真正的原理是什么呢？

>答：以上几种实现方式，都借助了ClassLoader的线程安全机制。

先解释清楚为什么说都借助了`ClassLoader。

从后往前说，先说两个饿汉，其实都是通过定义静态成员变量，以保证`instance`可以在类初始化的时候被实例化。那为啥让
`instance`在类初始化的时候被实例化就能保证线程安全了呢？因为类的初始化是由`ClassLoader`完成的，这其实就是
利用了`ClassLoader`的线程安全机制啊。

再说静态内部类，这种方式和两种饿汉方式只有细微差别，只是做法上稍微优雅一点。这种方式是`Singleton`类被装载了，
`instance`不一定被初始化。因为`SingletonHolder`类没有被主动使用，只有显示 通过调用getInstance方法时，
才会显示装载`SingletonHolder`类，从而实例化`instance`。。。但是，原理和饿汉一样。

最后说枚举，其实，如果把枚举类进行反序列化，你会发现他也是使用了`static final`来修饰每一个枚举项。

至于为什么ClassLoader加载类是线程安全的，这里可以先直接回答：`ClassLoader`的`loadClass`方法在加载类的
时候使用了`synchronized`关键字。也正是因为这样，除非被重写，这个方法默认在整个装载过程中都是同步的（线程安全的）
。所以虽然没有显示使用`synchronized`和`lock`，但是还是间接的用到了！！

### 真正的不使用`synchronized`和`lock`，如何实现一个线程安全的单例？
如果不那么吹毛求疵的话，可以使用枚举、静态内部类以及饿汉模式来实现单例模式。如上，但是上面这几种方法其实底层
也都用到了`synchronized` ，那么有没有什么办法可以不使用`synchronized`和`lock`，如何实现一个线程安全的
单例？

答案是有的，那就是`CAS`。关于Holis的博客中专门有一篇文章介绍过他，很多乐观锁都是基于`CAS`实现的。这里简单介绍
一下，详细内容见[乐观锁的一种实现方式](http://www.hollischuang.com/archives/1537)
>CAS是项乐观锁技术，当多个线程尝试使用CAS同时更新同一个变量时，只有其中一个线程能更新变量的值。而其它线程都失败，
失败的线程并不会被挂起，而是被告知这次竞争中失败，并可以再次尝试。

在JDK1.5中新增`java.util.concurrent`(J.U.C)就是建立在CAS之上的。相对于对于`synchronized`这种阻塞算法，
CAS是非阻塞算法的一种常见实现。所以J.U.C在性能上有了很大的提升。

借助CAS（AtomicReference)实现单例模式：
```java
public class Singleton{
    private static final AtomicReference<Singleton> INSTANCE = new AtomicReference<Singleton>();
    
    private Singleton(){}
    
    public static Singleton getInstance()
    {
        for(;;)
        {
            Singleton singleton = INSTANCE.get();
            if(null != singleton)
            {
                return singleton;
            }
            
            singleton = new Singleton();
            if(INSTANCE.compareAndSet(null,singleton))
            {
                return singleton;
            }
        }
    }
}
```
用CAS的好处在于不需要使用传统的锁机制来保证线程安全，CAS是一种基于忙等待的算法，依赖底层硬件的实现，相对
于锁它没有线程切换和阻塞的额外消耗，可以支持较大的并行度。

CAS的一个重要缺点在于如果忙等待一直执行不成功（一直在死循环中），会对CPU造成较大的执行开销.