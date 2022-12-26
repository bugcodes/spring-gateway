package com.bugcoder.component;

import cn.hutool.core.convert.Convert;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zbj
 * @date 2022/12/20
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    public static final String AUTHORITY_PREFIX = "ROLE_";

    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        URI uri = object.getExchange().getRequest().getURI();
        Object obj = redisTemplate.opsForHash().get(RESOURCE_ROLES_MAP, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        authorities = authorities.stream().map(au -> au = AUTHORITY_PREFIX + au).collect(Collectors.toList());
        return authentication.filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
