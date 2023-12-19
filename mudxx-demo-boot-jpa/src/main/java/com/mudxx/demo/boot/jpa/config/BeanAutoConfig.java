package com.mudxx.demo.boot.jpa.config;

import com.mudxx.component.redis.utils.StringRedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author laiw
 * @date 2023/11/22 15:38
 */
@Configuration
public class BeanAutoConfig {

    @Bean
    public StringRedisUtils getStringRedisUtils(StringRedisTemplate stringRedisTemplate) {
        return new StringRedisUtils(stringRedisTemplate);
    }

}
