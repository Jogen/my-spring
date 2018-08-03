package com.hu.myspring;

import com.hu.myspring.aop.advice.AroundAdvice;
import com.hu.myspring.aop.annotation.Aspect;
import com.hu.myspring.aop.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-03 10:25
 */
@Slf4j
@Order(2)
@Aspect(pointcut = "@within(com.hu.myspring.core.annotation.Controller)")
public class DoodleAspect2 implements AroundAdvice {
    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        log.info("-----------before  DoodleAspect2-----------");
        log.info("class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("-----------after  DoodleAspect2-----------");
        log.info("class: {}, method: {}", clz, method.getName());
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        log.error("-----------error  DoodleAspect2-----------");
        log.error("class: {}, method: {}, exception: {}", clz, method.getName(), e.getMessage());
    }
}
