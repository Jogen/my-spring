package com.hu.myspring.mvc;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatcherServlet 所有http都由此Servlet转发
 *
 * @author hujunfei
 * @date 2018-08-03 16:12
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private ControllerHandler controllerHandler = new ControllerHandler();

    private ResultRender resultRender = new ResultRender();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码方式
        request.setCharacterEncoding("UTF-8");
        // 获取请求方法和请求路径
        String requestMethod = request.getMethod();
        String requestPath = request.getPathInfo();
        log.info("[DoodleConfig] {} {}", requestMethod, requestPath);
        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        }

        ControllerInfo controllerInfo = controllerHandler.getController(requestMethod, requestPath);
        log.info("{}", controllerInfo);
        if (null == controllerInfo) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        resultRender.invokeController(request, response, controllerInfo);
    }
}
