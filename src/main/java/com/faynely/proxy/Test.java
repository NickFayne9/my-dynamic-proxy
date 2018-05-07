package com.faynely.proxy;

/**
 * 测试类
 *
 * @author NickFayne 2018-05-02 13:31
 */
public class Test {
    public static void main(String[] args) {
        NickProxyTargetObject nickProxyTargetObject = new NickProxyTargetObject();
        NickProxyObject nickProxyObject = new NickProxyObject(nickProxyTargetObject);

        INickProxyTargetObject nickProxyTargetObjectProxy = (INickProxyTargetObject) NickProxy.newProxyInstance(new NickClassloader(), nickProxyTargetObject.getClass().getInterfaces(),nickProxyObject);
        nickProxyTargetObjectProxy.func();
    }
}