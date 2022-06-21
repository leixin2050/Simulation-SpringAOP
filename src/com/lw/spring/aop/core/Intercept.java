package com.lw.spring.aop.core;

/**
 * @author shkstart
 * @create 2022-06-20 21:59
 * 拦截器接口，提供给用户定义，包含了前置拦截与后者拦截
 */
public interface Intercept {
    boolean before();
    void after();
}
