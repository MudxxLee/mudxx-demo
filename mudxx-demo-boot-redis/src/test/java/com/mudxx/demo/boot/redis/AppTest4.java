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
import java.util.Arrays;
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
public class AppTest4 {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            5,
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
            0);

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = AppTest4.getJedis();

//        for (int i = 0; i < 1; i++) {
//            jedis.zadd("OrderId", DateUtil.currentSeconds() + RandomUtil.randomInt(-600, 600), "O-" + IdUtil.simpleUUID());
//        }
//        doProcessExpiredOrdersForZSet(jedis);

//        String s = "";
//
//        String[] split = s.split("\n");
//        Arrays.stream(split).forEach(e -> {
//            String[] s1 = e.split("_");
//            long score = DateUtil.parse(s1[1]).getTime() / 1000;
////            System.out.println(score);
////            System.out.println(s1[0]);
//            jedis.zadd("pgw:cache:unexpired:orders", score, s1[0]);
//        });

        THREAD_POOL_EXECUTOR.shutdown();
    }

    /**
     * 处理缓存订单
     * <p>
     * 单次任务执行数量有限
     */
    private static void doProcessExpiredOrdersForZSet(Jedis jedis) {
        final int taskMax = 30;
        int taskCount = 0;
        long start = 0;
        long stop = 9;
        do {
            // 获取指定范围内的订单（按分数从低到高排序）
            Set<Tuple> tupleSets = jedis.zrangeWithScores("OrderId", start, stop);
            if (ObjectUtils.isEmpty(tupleSets)) {
                break;
            }

            // 过滤出需要处理的订单
            List<String> orderIds = tupleSets.stream().filter(AppTest4::canRemove).map(Tuple::getElement).collect(Collectors.toList());
            if (ObjectUtils.isEmpty(orderIds)) {
                break;
            }

            // 处理已过期的订单
            doProcessExpiredOrders(orderIds);

            // 移除ZSet指定的成员
            Long aLong = jedis.zrem("OrderId", orderIds.toArray(new String[0]));
            log.info("---------------- 定时处理已过期的订单 第个集合执行结果: zrem={}", aLong);


            // 当前集合已存在未过期的
            if (orderIds.size() < tupleSets.size()) {
                break;
            }

            // 终止条件
            taskCount += orderIds.size();

        } while (taskCount <= taskMax);
    }

    /**
     * 处理已过期的订单
     * <p>
     * 单次任务执行数量有限
     */
    private static void doProcessExpiredOrders(List<String> orderIds) {
        List<List<String>> splits = CollectionTaskUtils.split(orderIds, 5);
        splits.forEach(ids -> {
            log.info("---------------- 定时处理已过期的订单 第{}个集合执行", splits.indexOf(ids));
            // 更新过期订单状态
            CompletableFuture.runAsync(() -> {
//                int i = 1/0;
                log.info("---------------- 定时处理已过期的订单 第{}个集合执行结果: update={}", splits.indexOf(ids), ids.size());
            }, THREAD_POOL_EXECUTOR);
        });
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
