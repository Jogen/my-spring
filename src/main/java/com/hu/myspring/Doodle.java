package com.hu.myspring;

import com.hu.myspring.aop.Aop;
import com.hu.myspring.core.BeanContainer;
import com.hu.myspring.ioc.Ioc;
import com.hu.myspring.mvc.server.Server;
import com.hu.myspring.mvc.server.TomcatServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-09 14:17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class Doodle {

    @Getter
    private static Configuration configuration = Configuration.builder().build();

    private static Server server;

    public static void run(Class<?> bootClass) {
        run(Configuration.builder().bootClass(bootClass).build());
    }

    public static void run(Class<?> bootClass, int port) {
        run(Configuration.builder().bootClass(bootClass).serverPort(port).build());
    }

    public static void run(Configuration configuration) {
        new Doodle().start(configuration);
    }

    /**
     * 初始化
     * @param configuration
     */
    private void start(Configuration configuration) {
        try {
            Doodle.configuration = configuration;
            String basePackage = configuration.getBootClass().getPackage().getName();
            BeanContainer.getInstance().loadBeans(basePackage);
            new Aop().doAop();
            new Ioc().doIoc();

            server = new TomcatServer(configuration);
            server.startServer();;
        } catch (Exception e) {
            log.error("Doodle启动失败", e);
        }
    }
}
