package com.faynely.proxy;

import java.lang.reflect.Method;

/**
 * 动态代理类
 * @author NickFayne 2018-05-02 13:32
 */
public class NickProxyObject implements NickInvocationHandler{

    private INickProxyTargetObject nickProxyTargetObject;

    public NickProxyObject(INickProxyTargetObject nickProxyTargetObject) {
        this.nickProxyTargetObject = nickProxyTargetObject;
    }

    @Override
    public Object invoke(Object object, Method method, Object args) {
        System.out.println("在你之前来点东西...");
        try {
            return method.invoke(this.nickProxyTargetObject, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
