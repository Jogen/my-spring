package com.hu.myspring.aop.advice;

import java.lang.reflect.Method;

/**
 * 返回通知接口
 *
 * @author hujunfei
 * @date 2018-08-02 11:49
 */
public interface AfterReturningAdvice extends Advice {

    /**
     * 返回后方法
     * @param clz
     * @param returnValue
     * @param method
     * @param args
     * @throws Throwable
     */
    void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable;
}
