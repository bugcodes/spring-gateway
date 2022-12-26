package com.bugcoder.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author zbj
 * @date 2022/12/19
 */
@RestController
public class KeyPairController {

    @Resource
    KeyPair keyPair;

    @RequestMapping(value = "/rsa/publicKey", method = RequestMethod.GET)
    public Map<String, Object> getKey(){
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey rsaKey = new RSAKey.Builder(publicKey).build();
        return new JWKSet(rsaKey).toJSONObject();
    }
}
