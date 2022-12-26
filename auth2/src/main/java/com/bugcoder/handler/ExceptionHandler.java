package com.bugcoder.handler;

import com.bugcoder.api.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author zbj
 * @date 2022/12/21
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(OAuth2Exception.class)
    @ResponseBody
    public CommonResult handleOauth2(OAuth2Exception e) {
        return CommonResult.failed(e.getMessage());
    }
}
