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
