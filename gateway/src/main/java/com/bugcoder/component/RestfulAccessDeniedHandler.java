package com.bugcoder.component;

import cn.hutool.json.JSONUtil;
import com.bugcoder.api.CommonResult;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @author zbj
 * @date 2022/12/20
 */
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body = JSONUtil.toJsonStr(CommonResult.forbidden(denied.getMessage()));
        DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("utf-8")));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
