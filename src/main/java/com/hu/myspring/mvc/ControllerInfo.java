package com.hu.myspring.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ControllerInfo 存储Controller相关信息
 *
 * @author hujunfei
 * @date 2018-08-03 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerInfo {

    private Class<?> controllerClass;

    private Method invokeMethod;

    private Map<String, Class<?>> methodParameter;
}
