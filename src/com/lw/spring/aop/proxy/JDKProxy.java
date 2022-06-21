package com.lw.spring.aop.proxy;

import com.lw.spring.aop.core.MethodInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shkstart
 * @create 2022-06-20 21:26
 */
public class JDKProxy {

    private MethodInvoker methodInvoker;

    public JDKProxy() {
        this.methodInvoker = new MethodInvoker();
    }

    //允许外部set进来MethodInvoker
    public void setMethodInvoker(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    <T> T getProxy(Object object) {
        Class<?> klass = object.getClass();
        ClassLoader classLoader = klass.getClassLoader();
        Class<?>[] interfaces = klass.getInterfaces();

        return (T) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                methodInvoker.setMethod(method);
                methodInvoker.setArgs(args);
                methodInvoker.setObject(object);

                //由methodInvoker执行完毕后返回值
                return methodInvoker.methodInvoke();
            }
        });
    }

    <T> T getProxy(Class<?> klass) {
        try {
            return getProxy(klass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
