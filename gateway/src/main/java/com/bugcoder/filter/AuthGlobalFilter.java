package com.bugcoder.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 登录用户jwt转化成用户信息
 *
 * @author zbj
 * @date 2022/12/20
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    ModifyRequestBodyGatewayFilterFactory requestBodyGatewayFilterFactory;
    @Resource
    RequestBodyRewrite requestBodyRewrite;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        HttpMethod method = exchange.getRequest().getMethod();
        if (StringUtils.isEmpty(token)){
            chain.filter(exchange);
        }
        try {
            String bearer = token.replace("bearer", "");
            JWSObject jwsObject = JWSObject.parse(bearer);
            String userStr = jwsObject.getPayload().toString();
            log.info("AuthGlobalFilter.filter() user:{}",userStr);
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().header("user", userStr).build();
            exchange = exchange.mutate().request(serverHttpRequest).build();
        }catch (ParseException e){
            e.printStackTrace();
        }
        if (method.matches(RequestMethod.GET.name())){
            return chain.filter(exchange);
        }
        return requestBodyGatewayFilterFactory
                .apply(new ModifyRequestBodyGatewayFilterFactory.Config().setRewriteFunction(String.class, String.class,requestBodyRewrite))
                .filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
