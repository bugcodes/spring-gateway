package com.bugcoder.config;

import cn.hutool.core.util.ArrayUtil;
import com.bugcoder.component.AuthorizationManager;
import com.bugcoder.component.RestAuthenticationEntryPoint;
import com.bugcoder.component.RestfulAccessDeniedHandler;
import com.bugcoder.filter.IgnoreUrlsRemoveJwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author zbj
 * @date 2022/12/20
 */
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Resource
    private  AuthorizationManager authorizationManager;

    @Resource
    private  IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    private  RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Resource
    private  RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private  IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    public static final String AUTHORITY_PREFIX = "ROLE_";

    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
        httpSecurity.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtConverter());
        httpSecurity.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
        httpSecurity.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        httpSecurity.authorizeExchange().pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()
                .anyExchange().access(authorizationManager).and().exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().csrf().disable();
        return httpSecurity.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
