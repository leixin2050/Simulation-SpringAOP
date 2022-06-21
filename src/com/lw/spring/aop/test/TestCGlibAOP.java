package com.lw.spring.aop.test;

import com.lw.spring.aop.core.Intercept;
import com.lw.spring.aop.proxy.LWProxy;

/**
 * @author shkstart
 * @create 2022-06-21 11:45
 */
public class TestCGlibAOP {
    public static void main(String[] args) {
        LWProxy lwProxy = new LWProxy(LWProxy.CGLIB_PROXY);
        lwProxy.addIntercept("OtherClass.*(*)", new Intercept() {
            @Override
            public boolean before() {
                System.out.println("前置拦截A！");
                return true;
            }

            @Override
            public void after() {
                System.out.println("后置拦截A!");
            }
        });

        lwProxy.addIntercept("OtherClass.*(*)", new Intercept() {
            @Override
            public boolean before() {
                System.out.println("前置拦截B！");
                return true;
            }

            @Override
            public void after() {
                System.out.println("后置拦截B!");
            }
        });

        OtherClass otherClass = new OtherClass();
        OtherClass otherClass1 = lwProxy.getProxy(otherClass);
        otherClass1.doSome();
    }
}
