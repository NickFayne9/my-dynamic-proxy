# 自定义的动态代理概述

JDK 的动态代理的实质就是 JDK 生成了需要被代理接口的实现类

直接上图，下面一张是动态代理生成过程的类图：

![动态代理生成过程](https://github.com/NickFayne9/git-resource/blob/master/dynamic-proxy/dynamicProxyGenerate.png?raw=true)

这个类是通过 `Proxy.newProxyInstance(目标类加载器，目标类实现的接口，代理类实例)` 生成的。

下面再来看一看调用过程：

![动态代理调用过程](https://github.com/NickFayne9/git-resource/blob/master/dynamic-proxy/dynamicProxyProcess.png?raw=true)

# JDK 动态代理原理

JDK 如何动态生成类？

`public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)` 这个方法很关键。

1. 根据 interfaces 生成动态代理类源码。
2. 将源码写入磁盘或者内存。
3. 编译编码生成 .class 文件。
4. 通过类加载器，将 .class 文件加载至 JVM 中。
5. 通过反射构造方法，生成代理类实例。

# 由 JDK 生成的动态类的源码示例

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.nick.ITarget;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy implements ITarget {
    private static Method m1;
    private static Method m3;
    private static Method m2;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void func1() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m3 = Class.forName("com.nick.ITarget").getMethod("func1");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}

```