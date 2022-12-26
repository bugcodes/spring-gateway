package com.bugcoder.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author zbj
 * @date 2022/12/20
 */
@Data
@Builder
public class TokenDTO {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;
}
