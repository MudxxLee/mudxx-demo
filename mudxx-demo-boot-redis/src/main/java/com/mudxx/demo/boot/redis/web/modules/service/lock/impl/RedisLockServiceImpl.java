package com.mudxx.demo.boot.redis.web.modules.service.lock.impl;

import com.mudxx.common.utils.RandomUtils;
import com.mudxx.component.redis.lock.spring.StringRedisLockHelper;
import com.mudxx.demo.boot.redis.web.modules.service.lock.IRedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author laiw
 * @date 2023/7/18 16:34
 */
@Service
public class RedisLockServiceImpl implements IRedisLockService {

    @Autowired
    private StringRedisLockHelper stringRedisLockHelper;

    @Autowired
    private ThreadPoolExecutor bizCommonExecutor;

    private static final long expireSecond = 5L;
    private static final long maxWaitTime = 6L;
    private static final long retryIntervalMillis = 200L;

//    @PostConstruct
    public void test() {
        for (int i = 0; i < 50; i++) {
            final int randomInt = RandomUtils.getRandomInt(5);
            String lockKey = "my-lock-key-" + randomInt;
            String lockValue = String.valueOf(randomInt);
            int idx = i;
            bizCommonExecutor.submit(() -> {
                boolean locked = stringRedisLockHelper.retryLock(lockKey, lockValue, expireSecond, maxWaitTime, retryIntervalMillis);
                if (locked) {
                    try {
                        System.out.println("[i=" + idx + "]" + lockKey + " lock");
                        // 模拟业务逻辑处理
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        stringRedisLockHelper.unLock(lockKey, lockValue);
                        System.out.println("[i=" + idx + "]" + lockKey + " unLock lock");
                    }
                } else {
                    // 获取锁失败的处理逻辑
                    System.out.println("[i=" + idx + "]" + lockKey + " failed");
                }
            });
        }

    }

}
