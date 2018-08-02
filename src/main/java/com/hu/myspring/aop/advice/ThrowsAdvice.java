package com.hu.myspring.aop.advice;

import java.lang.reflect.Method;

/**
 * 异常通知接口
 *
 * @author hujunfei
 * @date 2018-08-02 11:51
 */
public interface ThrowsAdvice extends Advice {

    /**
     * 异常方法
     * @param clz
     * @param method
     * @param args
     * @param e
     */
    void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e);
}
