package com.hu.myspring.ioc;

import com.hu.myspring.core.BeanContainer;
import com.hu.myspring.ioc.annotation.Autowired;
import com.hu.myspring.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 10:53
 */
@Slf4j
public class Ioc {

    private BeanContainer beanContainer;

    public Ioc() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doIoc() {
        // 遍历所有的bean
        for (Class<?> clz : beanContainer.getClasses()) {
            final Object targetBean = beanContainer.getBean(clz);
            Field[] fields = clz.getDeclaredFields();
            // 遍历bean中的所有属性
            for (Field field : fields) {
                // 如果该属性被Autowired注解， 则对其注入
                if (field.isAnnotationPresent(Autowired.class)) {
                    final Class<?> fieldClass = field.getType();
                    Object fieldValue = getClassInstance(fieldClass);
                    if (null != fieldValue) {
                        ClassUtil.setField(field, targetBean, fieldValue);
                    } else {
                        throw new RuntimeException("无法注入对应的类，目标类型：" + fieldClass.getName());
                    }
                }
            }
        }
    }

    /**
     * 根据Class获取其实例或者实现类
     * @param clz
     * @return
     */
    private Object getClassInstance(final Class<?> clz) {
        return Optional.ofNullable(beanContainer.getBean(clz))
                .orElseGet(() -> {
                    Class<?> implementClass = getImplementClass(clz);
                    if (null != implementClass) {
                        return beanContainer.getBean(implementClass);
                    }
                    return null;
                });
    }

    /**
     * 获取接口的实现类
     * @param interfaceClass
     * @return
     */
    private Class<?> getImplementClass(final Class<?> interfaceClass) {
        return beanContainer.getClassBySuper(interfaceClass)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
