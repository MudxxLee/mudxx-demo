package com.mudxx.demo.boot.redis.config;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import cn.hippo4j.core.executor.DynamicThreadPool;
import cn.hippo4j.core.executor.support.ThreadPoolBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author laiw
 * @date 2023/5/4 11:42
 */
@Configuration
@EnableDynamicThreadPool
public class Hippo4jConfig {

    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor bizCommonExecutor() {
        String threadPoolId = "biz-redis-common";
        return ThreadPoolBuilder.builder()
                .threadFactory(threadPoolId)
                .threadPoolId(threadPoolId)
                .dynamicPool()
                .build();
    }

}
