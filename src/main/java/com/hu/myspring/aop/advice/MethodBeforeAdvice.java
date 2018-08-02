package com.hu.myspring.aop.advice;

import java.lang.reflect.Method;

/**
 * 前置通知接口
 *
 * @author hujunfei
 * @date 2018-08-02 11:48
 */
public interface MethodBeforeAdvice extends Advice {

    /**
     * 前置方法
     * @param clz
     * @param method
     * @param args
     * @throws Throwable
     */
    void before(Class<?> clz, Method method, Object[] args) throws Throwable;
}
