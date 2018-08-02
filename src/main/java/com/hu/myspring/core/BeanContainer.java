package com.hu.myspring.core;

import com.hu.myspring.aop.annotation.Aspect;
import com.hu.myspring.core.annotation.Component;
import com.hu.myspring.core.annotation.Controller;
import com.hu.myspring.core.annotation.Repository;
import com.hu.myspring.core.annotation.Service;
import com.hu.myspring.util.ClassUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 是否加载Bean
     */
    private boolean isLoadBean = false;

    /**
     * 加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION =
            Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    /**
     * 获取bean容器实例
     * @return
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        /**
         * 枚举实现单例
         */
        HOLDER;
        private BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    public Object getBean(Class<?> clz) {
        if (null == clz) {
            return null;
        }
        return beanMap.get(clz);
    }

    /**
     * 获取所有bean
     * @return
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    public Object addBean(Class<?> clz, Object bean) {
        return beanMap.put(clz, bean);
    }

    public void removeBean(Class<?> clz) {
        beanMap.remove(clz);
    }

    public int size() {
        return beanMap.size();
    }

    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 通过注解获取bean的class集合
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        return beanMap.keySet()
                .stream()
                .filter(clz -> clz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> getClassBySuper(Class<?> superClass) {
        return beanMap.keySet()
                .stream()
                .filter(superClass::isAssignableFrom)
                .filter(clz -> !clz.equals(superClass))
                .collect(Collectors.toSet());
    }

    /**
     * 扫描加载所有的bean
     * @param basePackage
     */
    public void loadBeans(String basePackage) {
        if (isLoadBean()) {
            log.warn("bean已经加载");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.getPackageClass(basePackage);
        classSet.stream()
                .filter(clz -> {
                    for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                        if (clz.isAnnotationPresent(annotation)) {
                            return true;
                        }
                    }
                    return false;
                })
                .forEach(clz -> beanMap.put(clz, ClassUtil.newInstance(clz)));
        isLoadBean = true;
    }

    public boolean isLoadBean() {
        return isLoadBean;
    }
}
