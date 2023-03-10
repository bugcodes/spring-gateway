//package com.bugcoder.component;
//
//import com.bugcoder.service.UserServiceImpl;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
//
//import java.security.KeyPair;
//import java.util.ArrayList;
//import java.util.List;
///**
// * @author zbj
// * @date 2022/12/20
// */
//@AllArgsConstructor
//@Configuration
//@EnableAuthorizationServer
//public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserServiceImpl userDetailsService;
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenEnhancer jwtTokenEnhancer;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("client-app")
//                .secret(passwordEncoder.encode("bugcoder"))
//                .scopes("all")
//                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(86400);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> delegates = new ArrayList<>();
//        delegates.add(jwtTokenEnhancer);
//        delegates.add(accessTokenConverter());
//        enhancerChain.setTokenEnhancers(delegates); //??????JWT??????????????????
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService) //?????????????????????????????????
//                .accessTokenConverter(accessTokenConverter())
//                .tokenEnhancer(enhancerChain);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.allowFormAuthenticationForClients();
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setKeyPair(keyPair());
//        return jwtAccessTokenConverter;
//    }
//
//    @Bean
//    public KeyPair keyPair() {
//        //???classpath??????????????????????????????
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "bugcoder".toCharArray());
//        return keyStoreKeyFactory.getKeyPair("jwt", "bugcoder".toCharArray());
//    }
//
//}
