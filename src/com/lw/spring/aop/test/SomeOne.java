package com.lw.spring.aop.test;

/**
 * @author shkstart
 * @create 2022-06-21 11:11
 */
public class SomeOne implements MyInterface {
    @Override
    public void doSome() {
        System.out.println("[doSome()]");
    }

    @Override
    public String doSum(String a, int b) {
        System.out.println("doSum()");
        return "[ " +  a + ": " + b + " ]";
    }
}
