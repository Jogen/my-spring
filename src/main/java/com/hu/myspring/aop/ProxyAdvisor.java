package com.hu.myspring.aop;

import com.hu.myspring.aop.advice.Advice;
import com.hu.myspring.aop.advice.AfterReturningAdvice;
import com.hu.myspring.aop.advice.MethodBeforeAdvice;
import com.hu.myspring.aop.advice.ThrowsAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理通知类
 *
 * @author hujunfei
 * @date 2018-08-02 11:55
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProxyAdvisor {

    /**
     * 通知
     */
    private Advice advice;

    /**
     * AspectJ 表达式切点匹配器
     */
    private ProxyPointcut pointcut;

    /**
     * 执行顺序
     */
    private int order;

    /**
     * 执行代理方法
     * @param target
     * @param targetClass
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    public Object doProxy(Object target, Class<?> targetClass, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!pointcut.matches(method)) {
            return proxy.invokeSuper(target, args);
        }
        Object result = null;
        if (advice instanceof MethodBeforeAdvice) {
            ((MethodBeforeAdvice) advice).before(targetClass, method, args);
        }
        try {
            // 执行目标类的方法
            result = proxy.invokeSuper(target, args);
            if (advice instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advice).afterReturning(targetClass, result, method, args);
            }
        } catch (Exception e) {
            if (advice instanceof ThrowsAdvice) {
                ((ThrowsAdvice) advice).afterThrowing(targetClass, method, args, e);
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }

    /**
     * 代理链
     * @param adviceChain
     * @return
     * @throws Throwable
     */
    public Object doProxy(AdviceChain adviceChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = adviceChain.getTargetClass();
        Method method = adviceChain.getMethod();
        Object[] args = adviceChain.getArgs();

        if (advice instanceof MethodBeforeAdvice) {
            ((MethodBeforeAdvice) advice).before(targetClass, method, args);
        }
        try {
            // 执行代理链方法
            result = adviceChain.doAdviceChain();
            if (advice instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advice).afterReturning(targetClass, result, method, args);
            }
        } catch (Exception e) {
            if (advice instanceof Throwable) {
                ((ThrowsAdvice) advice).afterThrowing(targetClass, method, args, e);
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }
}
