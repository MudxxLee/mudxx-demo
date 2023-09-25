package com.mudxx.demo.boot.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author laiw
 * @date 2023/4/3 17:02
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * description: 自定义redis缓存配置
     *
     * @param factory redis连接工厂
     * @return 返回redis模板对象
     */
    @Bean
    public RedisTemplate<String, Object> buildRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 设置键（key）的序列化采用StringRedisSerializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 设置值（value）的序列化采用Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 获取ObjectMapper对象
        ObjectMapper objectMapper = this.createObjectMapping();
        // 设置objectMapper属性
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * description: 创建ObjectMapper对象
     *
     * @return 返回ObjectMapper对象
     * @author laiwen
     * @date 2019-11-26 11:22:37
     */
    private ObjectMapper createObjectMapping() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return objectMapper;
    }

    /**
     * Redis缓存的序列化方式使用redisTemplate.getValueSerializer()，不在使用JDK默认的序列化方式
     *
     * @param redisConnectionFactory redis连接
     * @return RedisCacheManager
     **/
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 创建String和JSON序列化对象，分别对key和value的数据进行类型转换
        RedisSerializer<String> strSerializer = new StringRedisSerializer();
        // 设置值（value）的序列化采用Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 获取ObjectMapper对象
        ObjectMapper objectMapper = this.createObjectMapping();
        // 设置objectMapper属性
        jsonRedisSerializer.setObjectMapper(objectMapper);

        // 自定义缓存数据序列化方式和有效期限
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存有效期为1天
                .entryTtl(Duration.ofDays(1))
                // 使用strSerializer对key进行数据类型转换
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(strSerializer))
                // 使用jsonRedisSerializer对value的数据类型进行转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                // 对空数据不操作
                .disableCachingNullValues();

        return new CustomRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

}
