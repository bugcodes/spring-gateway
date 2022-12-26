package com.bugcoder.controller;

import cn.hutool.json.JSONUtil;
import com.bugcoder.component.LoginHandler;
import com.bugcoder.domain.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zbj
 * @date 2022/12/20
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    LoginHandler loginHandler;

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public UserDTO currentUser(){
        return loginHandler.getCurrentUser();
    }

    @RequestMapping(value = "/postRequest", method = RequestMethod.POST)
    public Map<String, String> postRequest(@RequestParam Map<String, String> paramsMap){
        log.info("paramMap: {}", JSONUtil.toJsonStr(paramsMap));
        return paramsMap;
    }
}
