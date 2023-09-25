package com.mudxx.demo.boot.redis;

import cn.hutool.core.util.NumberUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.Arrays;

/**
 * @author laiw
 * @date 2023/4/3 16:49
 */
public class CustomRedisCacheManager extends RedisCacheManager {

    /**
     * @param cacheWriter
     * @param defaultCacheConfiguration
     * @return
     * @description 提供默认构造器
     **/
    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * @param name        @Cacheable中的value
     * @param cacheConfig
     * @return org.springframework.data.redis.cache.RedisCache
     * @description 重写父类createRedisCache方法
     **/
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        //名称中存在#标记进行到期时间配置
        if (!name.isEmpty() && name.contains("#")) {
            String[] SPEL = name.split("#");
            System.out.println("SPEL=" + Arrays.toString(SPEL));
            if (NumberUtil.isLong(SPEL[1])) {
                //配置缓存到期时间
                long cycle = Long.parseLong(SPEL[1]);
                return super.createRedisCache(SPEL[0], cacheConfig.entryTtl(Duration.ofSeconds(cycle)));
            }
        }
        return super.createRedisCache(name, cacheConfig.entryTtl(Duration.ofDays(1L)));
    }
}
