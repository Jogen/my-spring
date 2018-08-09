package com.hu.myspring;

import lombok.Builder;
import lombok.Getter;

/**
 * 服务器相关配置
 *
 * @author hujunfei
 * @date 2018-08-09 11:41
 */
@Builder
@Getter
public class Configuration {

    /**
     * 启动类
     */
    private Class<?> bootClass;

    /**
     * 资源目录
     */
    @Builder.Default
    private String resourcePath = "src/main/resources";

    /**
     * jsp 目录
     */
    @Builder.Default
    private String viewPath = "/templates";

    /**
     * 静态文件目录
     */
    @Builder.Default
    private String assetPath = "/static/";

    /**
     * 端口号
     */
    @Builder.Default
    private int serverPort = 9090;

    /**
     * tomcat docBase目录
     */
    @Builder.Default
    private String docBase = "";

    /**
     * tomcat contextPath目录
     */
    @Builder.Default
    private String contextPath = "";
}
