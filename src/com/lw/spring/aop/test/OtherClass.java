package com.lw.spring.aop.test;

/**
 * @author shkstart
 * @create 2022-06-21 11:43
 */
public class OtherClass {

    public void doSome() {
        System.out.println("OtherClass: [doSome()]");
    }

    public String doSum(String a, int b) {
        System.out.println("OtherClass: doSum()");
        return "[ " +  a + ": " + b + " ]";
    }
}
