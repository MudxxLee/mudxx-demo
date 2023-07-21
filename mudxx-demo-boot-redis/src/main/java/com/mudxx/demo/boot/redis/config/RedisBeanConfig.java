package com.mudxx.demo.boot.redis.config;

import com.mudxx.component.redis.lock.spring.StringRedisLockHelper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author laiw
 * @date 2023/4/3 17:02
 */
@Configuration
@EnableCaching
public class RedisBeanConfig {

    /**
     * Redis缓存的序列化方式使用redisTemplate.getValueSerializer()，不在使用JDK默认的序列化方式
     *
     * @param redisTemplate
     * @return RedisCacheManager
     **/
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 若返回值为null，则不允许存储到Cache中
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));

        //设置默认过期时间是300秒
        redisCacheConfiguration.entryTtl(Duration.ofSeconds(300));

        return new CustomRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    @Bean
    public StringRedisLockHelper stringRedisLockHelper(StringRedisTemplate stringRedisTemplate) {
        return new StringRedisLockHelper(stringRedisTemplate);
    }

}
