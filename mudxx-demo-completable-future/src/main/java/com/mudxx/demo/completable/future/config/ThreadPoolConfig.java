package com.mudxx.demo.completable.future.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mudxx.common.thread.pool.executor.ThreadPoolExecutorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author laiw
 * @date 2023/9/25 11:51
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(value = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        return ThreadPoolExecutorFactory.initThreadPoolExecutor(
                10,
                10,
                60L,
                1000,
                new ThreadFactoryBuilder()
                        .setNameFormat("demo-completable-future-thread-%d")
                        .build());
    }

}
