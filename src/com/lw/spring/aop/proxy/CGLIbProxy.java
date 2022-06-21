package com.lw.spring.aop.proxy;

import com.lw.spring.aop.core.MethodInvoker;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @author shkstart
 * @create 2022-06-20 21:53
 */
public class CGLIbProxy {
    private MethodInvoker methodInvoker;

    public CGLIbProxy() {
        this.methodInvoker = new MethodInvoker();
    }

    //允许外部set进来MethodInvoker
    public void setMethodInvoker(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    <T> T getProxy(Object object) {
        Class<?> klass = object.getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(klass);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                methodInvoker.setMethod(method);
                methodInvoker.setArgs(args);
                methodInvoker.setObject(object);

                //由methodInvoker执行完毕后返回值
                return methodInvoker.methodInvoke();
            }
        });
        return (T) enhancer.create();
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
