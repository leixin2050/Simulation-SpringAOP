package com.lw.spring.aop.test;

import com.lw.spring.aop.core.Intercept;
import com.lw.spring.aop.proxy.LWProxy;

import java.lang.reflect.Method;

/**
 * @author shkstart
 * @create 2022-06-21 11:12
 */
public class TestAOP {
    public static void main(String[] args) {
        LWProxy lwProxy = new LWProxy(LWProxy.JDK_PROXY);
        lwProxy.addIntercept("MyInterface.*(*)", new Intercept() {
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

        lwProxy.addIntercept("MyInterface.*(*)", new Intercept() {
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


        SomeOne someOne = new SomeOne();
        MyInterface myInterface = lwProxy.getProxy(someOne);
        String str = myInterface.doSum("abc",12);
        System.out.println(str);
        System.out.println("-----------------------------");
        myInterface.doSome();

    }
}
