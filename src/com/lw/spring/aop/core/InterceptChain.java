package com.lw.spring.aop.core;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author shkstart
 * @create 2022-06-20 21:57
 * 拦截器链的实现类，包含拦截器的添加以及拦截器方法的执行顺序
 */
public class InterceptChain {
    private Intercept intercept;
    //拦截器链，多个拦截器时的添加方法
    private InterceptChain interceptChain;

    private String cutPoint;

    //初始化拦截器链
    public InterceptChain() {
        this.intercept = null;
        this.interceptChain = null;
    }

    //递归添加拦截器链
    public void addIntercept(String cutPoint, Intercept intercept) {
        //添加第一个拦截器,或者拦截器链的最后一个拦截器的添加，每次真正的添加
        if (this.intercept == null) {
            this.cutPoint = cutPoint;
            this.intercept = intercept;
            return;
        }
        //到达拦截器链的最后一个拦截器
        if (this.interceptChain == null) {
            this.interceptChain = new InterceptChain();
            this.interceptChain.addIntercept(cutPoint, intercept);
            return;
        }
        //递归找到最后一个拦截器
        this.interceptChain.addIntercept(cutPoint, intercept);
    }

    //下沉执行前置拦截
    public boolean doBefore(Method method) {
        //一旦存在前置拦截则不执行直接返回为false
        //可以嵌套的原因是当拦截器为null时，拦截器链必定为null
        if (this.intercept != null) {
            if (Pattern.matches(cutPoint, method.toString())) {
                if (!this.intercept.before()) {
                    return false;
                }
            }
            if (this.interceptChain != null) {
                //递归调用拦截器链中的拦截器的方法，如果只是调用doBefore(method),回陷入死循环
                this.interceptChain.doBefore(method);
            }
        }
        return true;
    }

    //冒泡执行拦截器
    public void doAfter(Method method) {
        //找到最后一个拦截器
        //可以嵌套的原因是当拦截器为null时，拦截器链必定为null
        if (this.interceptChain != null) {
            this.interceptChain.doAfter(method);
        }
        //到这里就是最后一个拦截器
        if (intercept != null) {
            if (Pattern.matches(cutPoint, method.toString())) {
                this.intercept.after();
            }
        }

    }
}
