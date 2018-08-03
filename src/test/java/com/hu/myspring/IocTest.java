package com.hu.myspring;

import com.hu.myspring.core.BeanContainer;
import com.hu.myspring.ioc.Ioc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 11:22
 */
@Slf4j
public class IocTest {

    @Test
    public void doIoc() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.hu.myspring");
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.helloForAspect();
    }
}
