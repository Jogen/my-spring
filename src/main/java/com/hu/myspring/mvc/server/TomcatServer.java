package com.hu.myspring.mvc.server;

import com.hu.myspring.Configuration;
import com.hu.myspring.Doodle;
import com.hu.myspring.mvc.DispatcherServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.jasper.servlet.JspServlet;

import java.io.File;
import java.net.URISyntaxException;

/**
 * tomcat 服务器
 *
 * @author hujunfei
 * @date 2018-08-09 11:50
 */
@Slf4j
public class TomcatServer implements Server {

    private Tomcat tomcat;

    public TomcatServer() {
        new TomcatServer(Doodle.getConfiguration());
    }

    public TomcatServer(Configuration configuration) {
        try {
            this.tomcat = new Tomcat();
            tomcat.setBaseDir(configuration.getDocBase());
            tomcat.setPort(configuration.getServerPort());

            File root = getRootFolder();
            File webContentFolder = new File(root.getAbsolutePath(), configuration.getResourcePath());
            StandardContext context = (StandardContext) tomcat.addWebapp(configuration.getContextPath(), webContentFolder.getAbsolutePath());
            context.setParentClassLoader(this.getClass().getClassLoader());

            WebResourceRoot resources = new StandardRoot(context);
            context.setResources(resources);
            // 添加jspServlet, defaultService和自己实现的dispatchServlet
            tomcat.addServlet("", "jspServlet", new JspServlet()).setLoadOnStartup(3);
            tomcat.addServlet("", "defaultServlet", new DefaultServlet()).setLoadOnStartup(1);
            tomcat.addServlet("", "dispatcherServlet", new DispatcherServlet()).setLoadOnStartup(0);

            context.addServletMappingDecoded("/templates/" + "*", "jspServlet");
            context.addServletMappingDecoded("/static/" + "*", "defaultServlet");
            context.addServletMappingDecoded("/*", "dispatcherServlet");

        } catch (Exception e) {
            log.error("初始化Tomcat失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startServer() throws Exception {
        tomcat.start();
        String address = tomcat.getServer().getAddress();
        int port = tomcat.getConnector().getPort();
        log.info("local address: http://{}:{}", address, port);
        tomcat.getServer().await();
    }

    @Override
    public void stopServer() throws Exception {
        tomcat.stop();
    }

    private File getRootFolder() {
        try {
            File root;
            String runningJarPath = this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            log.info("Tomcat: application resolved root folder: [{}]", root.getAbsolutePath());
            return root;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
