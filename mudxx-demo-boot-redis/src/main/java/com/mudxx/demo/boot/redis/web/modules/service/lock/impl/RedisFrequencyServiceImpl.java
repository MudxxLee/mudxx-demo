package com.mudxx.demo.boot.redis.web.modules.service.lock.impl;

import com.mudxx.component.redis.lock.spring.StringRedisFcyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author laiw
 * @date 2023/7/18 16:34
 */
@Service
public class RedisFrequencyServiceImpl {

    @Autowired
    private StringRedisFcyHelper stringRedisFcyHelper;

    @Autowired
    private ThreadPoolExecutor bizCommonExecutor;

    public static final int maxCount = 5;
    private static final long maxWaitTime = 6L;
    private static final long retryIntervalMillis = 50L;

//    @PostConstruct
    public void test() {
        for (int i = 0; i < 50; i++) {
            final String redisKey = "my-frequency-key-001";
            int idx = i;
            bizCommonExecutor.submit(() -> {
                try {
                    boolean result = stringRedisFcyHelper.retryIncr(redisKey, maxCount, maxWaitTime, retryIntervalMillis);
                    if (result) {
                        try {
                            System.out.println("[i=" + idx + "]" + " incr successfully ");
                            // 模拟业务逻辑处理
                            if (idx == 2) {
                                Thread.sleep(50000);
                            } else {
                                Thread.sleep(5000);
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            boolean decrement = stringRedisFcyHelper.tryDecr(redisKey);
                            System.out.println("[i=" + idx + "]" + " decr result " + decrement);
                        }
                    } else {
                        // 获取锁失败的处理逻辑
                        System.out.println("[i=" + idx + "]" + " incr failed ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }


}
