package com.hu.myspring;

import com.hu.myspring.core.annotation.Controller;
import com.hu.myspring.ioc.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 11:18
 */
@Controller
@Slf4j
public class DoodleController {

    @Autowired
    private DoodleService doodleService;

    public void helloForAspect() {
        log.info(doodleService.helloWorld());
    }
}
