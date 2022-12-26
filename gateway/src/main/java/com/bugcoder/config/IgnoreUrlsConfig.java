package com.bugcoder.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zbj
 * @date 2022/12/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {

    private List<String> urls;
}
