package com.lw.spring.aop.core;

/**
 * @author shkstart
 * @create 2022-06-20 21:51
 * 此类是一个单例的且线程安全的类，供用户获取代理执行的方法的参数与返回值
 */
public class MethodArgsAndResult {
    private Object[] args;
    private Object result;

    //单例
    private static MethodArgsAndResult methodArgsAndResult;

    private MethodArgsAndResult() {
    }

    public static MethodArgsAndResult getNewInstance() {
        if (methodArgsAndResult == null) {
            synchronized(MethodArgsAndResult.class) {
                if (methodArgsAndResult == null) {
                    methodArgsAndResult = new MethodArgsAndResult();
                }
            }
        }
        return methodArgsAndResult;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
