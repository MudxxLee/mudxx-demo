package com.mudxx.demo.anti.brush.config;

import com.mudxx.component.redis.lock.spring.StringRedisFcyHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author laiw
 * @date 2023/4/3 17:02
 */
@Configuration
public class RedisConfig {

    @Bean
    public StringRedisFcyHelper stringRedisFcyHelper(StringRedisTemplate stringRedisTemplate) {
        return new StringRedisFcyHelper(stringRedisTemplate);
    }

}
