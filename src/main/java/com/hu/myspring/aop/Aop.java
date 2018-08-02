package com.hu.myspring.aop;

import com.hu.myspring.aop.advice.Advice;
import com.hu.myspring.aop.annotation.Aspect;
import com.hu.myspring.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 14:10
 */
@Slf4j
public class Aop {

    private BeanContainer beanContainer;

    public Aop() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        beanContainer.getClassBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .forEach(clz -> {
                    final Advice advice = (Advice) beanContainer.getBean(clz);
                    Aspect aspect = clz. getAnnotation(Aspect.class);
                    beanContainer.getClassesByAnnotation(aspect.target())
                            .stream()
                            .filter(target -> !Advice.class.isAssignableFrom(target))
                            .filter(target -> !target.isAnnotationPresent(Aspect.class))
                            .forEach(target -> {
                                ProxyAdvisor advisor = new ProxyAdvisor(advice);
                                Object proxyBean = ProxyCreator.createProxy(target, advisor);
                                beanContainer.addBean(target, proxyBean);
                            });
                });
    }
}
