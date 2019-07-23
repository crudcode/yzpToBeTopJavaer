在单例模式中介绍了单例的概念、用途、实现方式、如何防止被序列化破坏等。单例模式在JDK源码中也有很多应用。

本文通过JDK(java 8)中几个典型的单例的使用来复习一下单例模式，并且通过这种实际应用来深入理解一下单例

的用法与实现方式。

### java.lang.Runtime
---
`Runtime`类封装了Java运行时的环境。每一个java程序实际上都是启动了一个JVM进程，那么每个JVM进程都是
对应这一个Runtime实例，
此实例是由JVM为其实例化的，每一个Java应用程序都有一 个Runtime类实例，使应用程序能够与其运行的环境相
连接。

由于Java是单进程的，所以,在一个JVM中，Runtime的实例应该只有一个，所以应该使用单例来实现。
```java

public class Runtime {
    private static Runtime currentRuntime = new Runtime();

    public static Runtime getRuntime() {
        return currentRuntime;
    }

    private Runtime() {
    }
}
```
以上代码为JDK中Runtime类的部分实现，可以看到，这其实是饿汉式单例模式。在该类第一次被classloader加
载的时候，这个实现就被创建出来了。
>一般不能实例化一个`Runtime`对象，应用程序 也不能创建自己的Runtime类实例，但可以通过`getRuntime`
方法获取当前`Runtime`运行时对象的引用。
### GUI中单例
除了`Runtime`是典型的单例以外。JDK中还有几个类是单例的，他们都是GUI中的类。这几个单例的类
和`Runtime`最大的区别就在于他们并不是饿汉模式，也就是他们都是惰性初始化的懒汉单例。如果分析其原因的话
也比较简单：那就是他们并不需要事先创建好，只要在第一次真正用到的时候再创建就可以了。因为很多时候我们并
不是用Java的GUI和其中的对象。如果使用饿汉单例的话会影响JVM的启动速度。

由于Java的强项并不是做GUI,所以这几个类其实并不会经常被用到。笔者也没用过。把代码贴这里，从单例的实
现的角度分析一下。

java.awt.Toolkit#getDeaultToolkit()
```java
public abstract class Toolkit {
    /**
     * The default toolkit.
     */
    private static Toolkit toolkit;

     public static synchronized Toolkit getDefaultToolkit() {
            if (toolkit == null) {
                java.security.AccessController.doPrivileged(
                        new java.security.PrivilegedAction<Void>() {
                    public Void run() {
                        Class<?> cls = null;
                        String nm = System.getProperty("awt.toolkit");
                        try {
                            cls = Class.forName(nm);
                        } catch (ClassNotFoundException e) {
                            ClassLoader cl = ClassLoader.getSystemClassLoader();
                            if (cl != null) {
                                try {
                                    cls = cl.loadClass(nm);
                                } catch (final ClassNotFoundException ignored) {
                                    throw new AWTError("Toolkit not found: " + nm);
                                }
                            }
                        }
                        try {
                            if (cls != null) {
                                toolkit = (Toolkit)cls.newInstance();
                                if (GraphicsEnvironment.isHeadless()) {
                                    toolkit = new HeadlessToolkit(toolkit);
                                }
                            }
                        } catch (final InstantiationException ignored) {
                            throw new AWTError("Could not instantiate Toolkit: " + nm);
                        } catch (final IllegalAccessException ignored) {
                            throw new AWTError("Could not access Toolkit: " + nm);
                        }
                        return null;
                    }
                });
                loadAssistiveTechnologies();
            }
            return toolkit;
        }
    }
```
上面的代码是`Toolkit`类的单例实现。这里类加载时只静态声明了私有`toolkit`并没有创建`Toolkit`
实例对象，延迟加载加快了JVM启动速度。
>单例模式作为一种创建模式，这里在依赖加载的时候应用了另一种创建对象的方式，不是new新的对象，因为
`Toolkit`本身是个抽象类不能实例化对象，而是通过反射机制加载 类并创建新的实例。

java.awt.GraphicsEnvironment#getLocalGraphicsEnvironment()
```java
public abstract class GraphicsEnvironment {
    private static GraphicsEnvironment localEnv;
    public static synchronized GraphicsEnvironment getLocalGraphicsEnvironment() {
        if (localEnv == null) {
            localEnv = createGE();
        }

        return localEnv;
    }
}
```
这里类加载时只静态声明了私有localEnv并没有创建实例对象。在GraphicsEnvironment类被第一次
调用时会创建该对象。这里没有贴出的createGE()方法也是通过反射的方式创建对象的。

java.awt.Desktop#getDesktop()
```java
public class Desktop {

    public static synchronized Desktop getDesktop(){
        if (GraphicsEnvironment.isHeadless()) throw new HeadlessException();
        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop API is not " +
                                                    "supported on the current platform");
        }

        sun.awt.AppContext context = sun.awt.AppContext.getAppContext();
        Desktop desktop = (Desktop)context.get(Desktop.class);

        if (desktop == null) {
            desktop = new Desktop();
            context.put(Desktop.class, desktop);
        }

        return desktop;
    }
}
```
上面的代码看上去和单例不太一样。但是实际上也是线程安全的懒汉式单例。
获取对象的时候先去环境容器中查找是否存在，不存在实例则创建一个实例。
>以上三个类的获取实例的方法都通过同步方法的方式保证了线程安全。

>Runtime类是通过静态初始化的方式保证其线程安全的。
### 总结
文中介绍了四个单例的例子，其中有一个饿汉式单例，三个是懒汉式单例。通过JDK中实际应用我们可以得出
以下结论：
>当一个类的对象只需要或者只可能有一个时，应该考虑单例模式。

>如果一个类的实例应该在JVM初始化时被创建出来，应该考虑使用饿汉式单例。

>如果一个类的实例不需要预先被创建，也许这个类的实例并不一定能用得上，也许这个类的实例创建过程比较耗费时间
，也许就是真的没必须提前创建。那么应该考虑懒汉式单例。

>在使用懒汉式单例的时候，应该考虑到线程的安全性问题
