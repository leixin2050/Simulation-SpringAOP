package com.lw.spring.aop.core;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.WildcardType;
import java.util.regex.Pattern;

/**
 * @author shkstart
 * @create 2022-06-20 21:27
 * 这个类是用来真正执行方法与返回结果的类
 * 包含了方法的三要素
 * 参数、反射得到的对象、反射得到的方法
 *
 * 还包含了切点的正则表达式的转换与匹配切点
 */
public class MethodInvoker {

    private Method method;
    private Object[] args;
    private Object object;

    //拦截器链
    private InterceptChain interceptChain;

    public MethodInvoker() {
        this.interceptChain = new InterceptChain();
    }

    //给切点添加拦截器,这里的方法名需要转换为切点，即全类名，使用正则表达式
    public void addIntercept(String methodName, Intercept intercept) {
        interceptChain.addIntercept(toRegex(methodName), intercept);
    }

    /**
     * 用户提供切点名，*.ClassName.methodName(*)
     * 例：*.MyInterface.*(*String,*int) 表示任意长度的前一个字符，前面必须有字符
     *  故 .* 表示任意字符
     *  用户只需输入简单的参数以及方法名，不确定的地方用*代替
     *  .*IMyInterface..*\(.*String,.*int\)
     *
     */
    private String toRegex(String methodName) {
        StringBuffer stringBuffer = new StringBuffer();
        String buffer = methodName.replace("*", ".*");

        int left =buffer.indexOf("(");
        int right = buffer.indexOf(")");
        //得到类名与方法名
        stringBuffer.append(".*").append(buffer.substring(0, left)).append("\\(");

        //按照逗号分割出参数字符串数组
        String[] args = buffer.substring(left + 1, right).split(",");
        for(int i = 0; i < args.length; i++) {
            stringBuffer.append((i == 0 ? "" : ",")).append(".*").append(args[i]);
        }
        stringBuffer.append("\\)");
        return stringBuffer.toString();
    }

    public Object methodInvoke() throws Exception {
        //存储切点的参数，且设置结果为null
        if (this.interceptChain == null) {
            return method.invoke(object, args);
        }

        MethodArgsAndResult methodArgsAndResult = MethodArgsAndResult.getNewInstance();
        methodArgsAndResult.setArgs(args);
        methodArgsAndResult.setResult(null);

        if (!this.interceptChain.doBefore(method)) {
            return null;
        }
        System.out.println("执行中");
        Object result = method.invoke(object, args);
        methodArgsAndResult.setResult(result);
        this.interceptChain.doAfter(method);
        return result;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
