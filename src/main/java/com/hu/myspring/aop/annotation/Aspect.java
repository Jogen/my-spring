package com.hu.myspring.aop.annotation;

import java.lang.annotation.*;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 11:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 目标代理类的范围
     * @return
     */
    Class<? extends Annotation> target();
}
