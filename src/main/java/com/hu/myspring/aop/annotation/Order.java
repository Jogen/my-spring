package com.hu.myspring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * aop排序
 *
 * @author hujunfei
 * @date 2018-08-03 9:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    /**
     * aop 顺序，值越大越先执行
     * @return
     */
    int value() default 0;
}
