package com.hu.myspring;

import com.hu.myspring.aop.advice.AroundAdvice;
import com.hu.myspring.aop.annotation.Aspect;
import com.hu.myspring.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 14:22
 */
@Slf4j
@Aspect(target = Controller.class)
public class DoodleAspect implements AroundAdvice {
    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        log.info("after DoodleAspect ---> class:{}, method:{}", clz.getName(), method.getName());
    }

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("before DoodleAspect ---> class:{}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        log.info("Error DoodleAspect ---> class:{}, method:{}", clz.getName(), method.getName());
    }
}
