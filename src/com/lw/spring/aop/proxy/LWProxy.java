package com.lw.spring.aop.proxy;

import com.lw.spring.aop.core.Intercept;
import com.lw.spring.aop.core.MethodInvoker;
import com.sun.crypto.provider.JceKeyStore;

/**
 * @author shkstart
 * @create 2022-06-20 21:55
 * 提供给用户使用的Proxy类，包含了JDK与CGLib
 */
public class LWProxy {
    public static final int CGLIB_PROXY = 1;
    public static final int JDK_PROXY = 0;
    private MethodInvoker methodInvoker;

    private int proxyChoice;

    public LWProxy() {
        this.methodInvoker = new MethodInvoker();
        this.proxyChoice = JDK_PROXY;
    }

    public LWProxy(int proxyChoice) {
        this();
        if (proxyChoice != CGLIB_PROXY && proxyChoice != JDK_PROXY) {
            this.proxyChoice = JDK_PROXY;
            return;
        }
        this.proxyChoice = proxyChoice;
    }

    public void setProxyChoice(int proxyChoice) {
        if (proxyChoice != CGLIB_PROXY && proxyChoice != JDK_PROXY) {
            this.proxyChoice = JDK_PROXY;
            return;
        }
        this.proxyChoice = proxyChoice;
    }

    public void addIntercept(String methodName, Intercept intercept) {
        this.methodInvoker.addIntercept(methodName, intercept);
    }

    public <T> T getProxy(Object object) {
        if (this.proxyChoice == CGLIB_PROXY) {
            CGLIbProxy cglIbProxy = new CGLIbProxy();
            cglIbProxy.setMethodInvoker(this.methodInvoker);
            return cglIbProxy.getProxy(object);
        } else {
            JDKProxy jdkProxy = new JDKProxy();
            jdkProxy.setMethodInvoker(this.methodInvoker);
            return jdkProxy.getProxy(object);
        }
    }

    public <T> T getProxy(Class<?> klass) {
        try {
            return getProxy(klass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
