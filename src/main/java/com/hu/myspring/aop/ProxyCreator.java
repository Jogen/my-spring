package com.hu.myspring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 代理类创建器
 *
 * @author hujunfei
 * @date 2018-08-02 14:14
 */
public final class ProxyCreator {

    /**
     * 创建代理类
     * @param targetClass
     * @param advisor
     * @return
     */
    public static Object createProxy(Class<?> targetClass, ProxyAdvisor advisor) {
        return Enhancer.create(targetClass,
                (MethodInterceptor) (target, method, args, proxy) ->
                        advisor.doProxy(target, targetClass, method, args, proxy));
    }
}
