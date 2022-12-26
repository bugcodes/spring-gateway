package com.bugcoder.service;

import cn.hutool.core.collection.CollUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 资源管理器
 *
 * @author zbj
 * @date 2022/12/19
 */
@Service
public class ResourceService {

    private Map<String, List<String>> resourceRolesMap;

    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/api/hello", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/api/currentUser", CollUtil.toList("ADMIN", "TEST"));
        resourceRolesMap.put("/api/postRequest", CollUtil.toList("ADMIN", "TEST"));
        redisTemplate.opsForHash().putAll(RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
