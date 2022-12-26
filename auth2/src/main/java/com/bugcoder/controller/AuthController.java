package com.bugcoder.controller;

import com.bugcoder.api.CommonResult;
import com.bugcoder.domain.TokenDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @author zbj
 * @date 2022/12/20
 */
@RestController
public class AuthController {

    @Resource
    TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public CommonResult<TokenDTO> accessToken(@RequestParam Map<String, String> paramsMap, Principal principal) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, paramsMap).getBody();
        TokenDTO tokenDTO = TokenDTO.builder().token(oAuth2AccessToken.getValue()).refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn()).tokenHead("Bearer ").build();
        return CommonResult.success(tokenDTO);
    }

}
