# 自定义的动态代理概述

JDK 的动态代理的实质就是 JDK 生成了需要被代理接口的实现类

直接上图，下面一张是动态代理生成过程的类图：

![动态代理生成过程](https://github.com/NickFayne9/git-resource/blob/master/dynamic-proxy/dynamicProxyGenerate.png?raw=true)

这个类是通过 `Proxy.newProxyInstance(目标类加载器，目标类实现的接口，代理类实例)` 生成的。

下面再来看一看调用过程：

![动态代理调用过程](https://github.com/NickFayne9/git-resource/blob/master/dynamic-proxy/dynamicProxyProcess.png?raw=true)