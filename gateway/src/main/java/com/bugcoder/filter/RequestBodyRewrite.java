package com.bugcoder.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zbj
 * @date 2022/12/23
 */
@Service
@Slf4j
public class RequestBodyRewrite implements RewriteFunction<String, String> {

    /**
     * Applies this function to the given arguments.
     *
     * @param serverWebExchange the first function argument
     * @param s                 the second function argument
     * @return the function result
     */
    @Override
    public Publisher<String> apply(ServerWebExchange serverWebExchange, String s) {
        log.info("原请求体{}", s);
        if (s.contains("appId")&&s.contains("platform")){
            log.info("获取到appId和platform");
            s += "&newKey=新增字段";
            log.info("增强后的请求体{}", s);
        }
        return Mono.just(s);
    }
}
