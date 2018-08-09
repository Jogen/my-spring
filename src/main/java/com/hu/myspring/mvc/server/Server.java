package com.hu.myspring.mvc.server;

/**
 * 服务器 interface
 *
 * @author hujunfei
 * @date 2018-08-09 11:48
 */
public interface Server {

    void startServer() throws Exception;

    void stopServer() throws Exception;
}
