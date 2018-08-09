package com.hu.myspring.sample;

import com.hu.myspring.core.annotation.Controller;
import com.hu.myspring.mvc.annotation.RequestMapping;
import com.hu.myspring.mvc.annotation.ResponseBody;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-09 14:26
 */
@Controller
@RequestMapping
public class DoodleController {

    @RequestMapping
    @ResponseBody
    public String hello() {
        return "hello doodle";
    }
}
