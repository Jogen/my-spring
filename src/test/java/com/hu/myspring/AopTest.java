package com.hu.myspring;

import com.hu.myspring.aop.Aop;
import com.hu.myspring.core.BeanContainer;
import com.hu.myspring.ioc.Ioc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 14:31
 */
@Slf4j
public class AopTest {

    @Test
    public void doAop() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.hu.myspring");
        new Aop().doAop();
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.hello();
    }
}
