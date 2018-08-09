package com.hu.myspring.mvc;

import com.alibaba.fastjson.JSON;
import com.hu.myspring.Doodle;
import com.hu.myspring.core.BeanContainer;
import com.hu.myspring.mvc.annotation.ResponseBody;
import com.hu.myspring.mvc.bean.ModelAndView;
import com.hu.myspring.util.CastUtil;
import com.hu.myspring.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 结果执行器
 *
 * @author hujunfei
 * @date 2018-08-03 15:04
 */
@Slf4j
public class ResultRender {

    private BeanContainer beanContainer;

    public ResultRender() {
        beanContainer = BeanContainer.getInstance();
    }

    public void invokeController(HttpServletRequest request, HttpServletResponse response, ControllerInfo controllerInfo) {
        // 1. 获取HttpServletRequest所有参数
        Map<String, String> requestParam = getRequestParams(request);
        // 2. 实例化调用方法要传入的参数值
        List<Object> methodParams = instantiateMethodArgs(controllerInfo.getMethodParameter(), requestParam);

        Object controller = beanContainer.getBean(controllerInfo.getControllerClass());
        Method invokeMethod = controllerInfo.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        // 3. 通过反射调用方法
        try {
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 4. 解析方法的返回值，选择返回页面或者json
        resultResolver(controllerInfo, result, request, response);
    }

    /**
     * 获取http中的参数
     * @param request
     * @return
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterMap().forEach((paramName, paramValues) -> {
            if (ValidateUtil.isNotEmpty(paramValues)) {
                paramMap.put(paramName, paramValues[0]);
            }
        });
        // TODO Body、Path、Header等方式的请求参数获取
        return paramMap;
    }

    private List<Object> instantiateMethodArgs(Map<String, Class<?>> methodParams, Map<String, String> requestParams) {
        return methodParams.keySet().stream().map(paramName -> {
            Class<?> type = methodParams.get(paramName);
            String requestValue = requestParams.get(paramName);
            Object value;
            if (null == requestValue) {
                value = CastUtil.primitiveNull(type);
            } else {
                value = CastUtil.convert(type, requestValue);
                // TODO 实现非原生类的参数实例化
            }
            return value;
        }).collect(Collectors.toList());
    }

    /**
     * Controller 方法执行后返回值解析
     * @param controllerInfo
     * @param result
     * @param request
     * @param response
     */
    private void resultResolver(ControllerInfo controllerInfo, Object result, HttpServletRequest request, HttpServletResponse response) {
        if (null == result) {
            return;
        }
        boolean isJson = controllerInfo.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            // 设置响应头
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // 向响应中写入数据
            try (PrintWriter writer = response.getWriter()) {
                writer.write(JSON.toJSONString(result));
                writer.flush();
            } catch (IOException e) {
                log.error("转发请求失败", e);
                // TODO 异常统一处理，400...
            }
        } else {
            String path;
            if (result instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) result;
                path = mv.getView();
                Map<String, Object> model = mv.getModel();
                if (ValidateUtil.isNotEmpty(model)) {
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
            } else if (result instanceof String) {
                path = (String) result;
            } else {
                throw new RuntimeException("返回类型不合法");
            }
            try {
//                request.getRequestDispatcher("/templates/" + path).forward(request, response);
                request.getRequestDispatcher(Doodle.getConfiguration().getResourcePath() + path).forward(request, response);
            } catch (Exception e) {
                log.error("转发请求失败", e);
                // TODO 异常统一处理
            }
        }
    }
}
