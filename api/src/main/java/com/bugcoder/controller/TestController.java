package com.bugcoder.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zbj
 * @date 2022/12/20
 */
@RestController
public class TestController {

    @RequestMapping(value = "/hello")
    public String test(){
        return "bugcoder";
    }
}
