package com.hu.myspring.mvc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-03 14:18
 */
public class ModelAndView {

    private String view;

    private Map<String, Object> model = new HashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public String getView() {
        return view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public ModelAndView addAllObjects(Map<String, ?> modelMap) {
        model.putAll(modelMap);
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

}
