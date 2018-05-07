package com.faynely.proxy;

import java.lang.reflect.Method;

/**
 * 对应 JDK 中的 InvocationHandler
 *
 * @author NickFayne 2018-05-02 13:29
 */
public interface NickInvocationHandler {

    Object invoke(Object object, Method method, Object args);
}
