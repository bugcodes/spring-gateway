package com.bugcoder.config;

import com.bugcoder.component.JwtTokenEnhancer;
import com.bugcoder.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器
 *
 * @author zbj
 * @date 2022/12/19
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private  PasswordEncoder passwordEncoder;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private  AuthenticationManager authenticationManager;

    @Resource
    private  JwtTokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> list = new ArrayList<>();
        list.add(jwtTokenEnhancer);
        list.add(accessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(list);
        endpoints.authenticationManager(authenticationManager).userDetailsService(userService).accessTokenConverter(accessTokenConverter()).tokenEnhancer(tokenEnhancerChain);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("client-app")
                .secret(passwordEncoder.encode("bugcoder"))
                .scopes("all").authorizedGrantTypes("password","refresh_token").accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(86400);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "bugcoder".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "bugcoder".toCharArray());
    }

}
