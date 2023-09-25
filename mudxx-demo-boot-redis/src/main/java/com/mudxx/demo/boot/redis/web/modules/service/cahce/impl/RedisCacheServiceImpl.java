package com.mudxx.demo.boot.redis.web.modules.service.cahce.impl;

import cn.hutool.core.util.StrUtil;
import com.mudxx.demo.boot.redis.constants.RedisCacheNames;
import com.mudxx.demo.boot.redis.web.modules.service.cahce.IRedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author laiw
 * @date 2023/7/5 11:10
 */
@Service
public class RedisCacheServiceImpl implements IRedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Cacheable(value = RedisCacheNames.TEST_GET, key = "#id", condition = "#id != null and #id != '' ", unless = "#result == null")
    @Override
    public String getEntity(String id) {
        System.out.println("----------执行get");
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return "获取id=" + id;
    }

    @CachePut(value = RedisCacheNames.TEST_GET, key = "#id")
    @Override
    public void saveEntity(String id) {
        System.out.println("----------执行save");
    }

    @CacheEvict(value = RedisCacheNames.TEST_GET, key = "#id")
    @Override
    public void deleteEntity(String id) {
        System.out.println("----------执行delete");
    }

    @Override
    public void set() {
        redisTemplate.opsForValue().set("test", "test1", 120, TimeUnit.SECONDS);
    }

    @Override
    public void get() {
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Override
    public void setStr() {
        stringRedisTemplate.opsForValue().set("test", "test2", 120, TimeUnit.SECONDS);
    }

    @Override
    public void getStr() {
        System.out.println("11111");
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
    }

}
