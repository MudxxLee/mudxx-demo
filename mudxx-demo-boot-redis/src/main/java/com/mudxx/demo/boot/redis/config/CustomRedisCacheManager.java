package com.mudxx.demo.boot.redis.config;

import cn.hutool.core.util.NumberUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * @author laiw
 * @date 2023/4/3 16:49
 */
public class CustomRedisCacheManager extends RedisCacheManager {

    /**
     * @description 提供默认构造器
     * @param cacheWriter
     * @param defaultCacheConfiguration
     * @return
     **/
    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * @description 重写父类createRedisCache方法
     * @param name @Cacheable中的value
     * @param cacheConfig
     * @return org.springframework.data.redis.cache.RedisCache
     **/
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        //名称中存在#标记进行到期时间配置
        if (!name.isEmpty() && name.contains("#")) {
            String[] split = name.split("#");
            if (split.length > 1 && NumberUtil.isLong(split[1])) {
                //配置缓存到期时间
                long cycle = Long.parseLong(split[1]);
                return super.createRedisCache(split[0], cacheConfig.entryTtl(Duration.ofSeconds(cycle)));
            }
        }
//        return super.createRedisCache(name, cacheConfig.entryTtl(Duration.ofDays(1L)));
        return super.createRedisCache(name, cacheConfig);
    }
}
