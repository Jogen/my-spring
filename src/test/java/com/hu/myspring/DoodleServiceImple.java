package com.hu.myspring;

import com.hu.myspring.core.annotation.Service;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-02 11:18
 */
@Service
public class DoodleServiceImple implements DoodleService {


    @Override
    public String helloWorld() {
        return "Hello World";
    }
}
