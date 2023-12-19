package com.mudxx.demo.boot.redis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author laiw
 * @date 2023/10/7 11:18
 */
@Slf4j
public class AppTest3 {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            2,
            10,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("order-job-thread-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    private static final JedisPool jedisPool = new JedisPool(
            new GenericObjectPoolConfig(),
            "r-gs532h5yt0vzvgtt02.redis.singapore.rds.aliyuncs.com",
            6379,
            6000,
            null,
            4);

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = AppTest3.getJedis();
        for (int i = 0; i < 100; i++) {
//            jedis.zadd("OrderId", DateUtil.currentSeconds() + RandomUtil.randomInt(-600, 600), "O-" + IdUtil.simpleUUID());
        }
        Set<Tuple> tuples = getZSetOperations(jedis);
        System.out.println("tuples.size(): " + tuples.size());
//        tuples.forEach(e -> {
//            String orderId = e.getElement();
//            System.out.println("1消费的订单OrderId为: " + orderId);
//        });
        List<List<Tuple>> splits = CollectionTaskUtils.split(tuples, 20);
        splits.forEach(items -> {
            List<String> orderIds = items.stream().map(Tuple::getElement).collect(Collectors.toList());
            // 更新过期订单状态
            CompletableFuture<Integer> future1 = CompletableFuture
                    .supplyAsync(items::size, THREAD_POOL_EXECUTOR)
                    .handle((result, ex) -> result);
//            CompletableFuture<Integer> future1 = CompletableFuture
//                    .supplyAsync(() -> {
//                        int i = 1/0;
//                        return items.size();
//                    }, THREAD_POOL_EXECUTOR)
//                    .handle((result, ex) -> result);
            // 移除已过期的订单缓存
            CompletableFuture<Long> future2 = CompletableFuture
                    .supplyAsync(() -> jedis.zrem("OrderId", orderIds.toArray(new String[0])), THREAD_POOL_EXECUTOR)
                    .handle((result, ex) -> result);
            // 组合结果
            CompletableFuture.allOf(future1, future2)
                    .thenAccept(v -> log.info("---------------- 定时处理已过期的订单 第{}个集合执行结果: update={}, remove={}",
                            splits.indexOf(items), future1.join(), future2.join()));
        });

        THREAD_POOL_EXECUTOR.shutdown();
    }

    private static Set<Tuple> getZSetOperations(Jedis jedis) {
        final long PAGE = 10;
        final long MAX = 50;
        long start = 0;
        long stop = 9;
        Set<Tuple> sets = new HashSet<>();
        do {
            Set<Tuple> tuples = jedis.zrangeWithScores("OrderId", start, stop);
            int len = ObjectUtils.isEmpty(tuples) ? 0 : tuples.size();
            if (len == 0) {
                break;
            }
            // 过滤已过期的
            tuples = tuples.stream()
                    .filter(AppTest3::canRemove)
                    .collect(Collectors.toSet());
            sets.addAll(tuples);
            if (tuples.size() < len) {
                break;
            }
            // 分页
            start += PAGE;
            stop += PAGE;
        } while (sets.size() <= MAX);
        return sets;
    }

    private static boolean canRemove(Tuple tuple) {
        double score = tuple.getScore();
        if (!Double.isFinite(score)) {
            // 值为NaN或无穷大
            return true;
        }
        long value;
        try {
            value = new BigDecimal(score).longValue();
        } catch (Exception e) {
            // 抛出ArithmeticException异常
            return true;
        }
        long current = System.currentTimeMillis() / 1000;
        return current >= value;
    }

}
