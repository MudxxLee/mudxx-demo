package com.mudxx.demo.boot.web.modules.service.cache.impl;

import cn.hutool.core.util.StrUtil;
import com.mudxx.demo.boot.web.modules.service.cache.ICacheTestService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author laiw
 * @date 2023/7/5 11:10
 */
@Service
public class CacheTestServiceImpl implements ICacheTestService {

    @Cacheable(value = "cache:test:get" + "#", key = "#id", condition = "#id != null and #id != '' ",unless = "#result == null")
    @Override
    public String getEntity(String id) {
        System.out.println("----------执行get");
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return "获取id=" + id;
    }

    @CachePut(value = "cache:test:get" + "#", key = "#id")
    @Override
    public void saveEntity(String id) {
        System.out.println("----------执行save");
    }

    @CacheEvict(value = "ttt", key = "#id")
    @Override
    public void deleteEntity(String id) {
        System.out.println("----------执行delete");
    }

}
