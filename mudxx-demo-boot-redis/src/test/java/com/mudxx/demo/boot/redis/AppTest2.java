package com.mudxx.demo.boot.redis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @author laiw
 * @date 2023/9/26 14:42
 */
public class AppTest2 {

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

    //消费者，取订单
    public static boolean consumerDelayMessage(Tuple tuple) {
        int score = (int) tuple.getScore();
        long currentSeconds = DateUtil.currentSeconds();
        return currentSeconds >= score;
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = AppTest2.getJedis();

        Set<Tuple> scores21 = jedis.zrangeWithScores("OrderId", 0, 0);
        scores21.forEach(e -> {
            String orderId = e.getElement();
            System.out.println("1消费的订单OrderId为: " + orderId);
        });

        jedis.zadd("OrderId", DateUtil.currentSeconds() + 60L, "O-" + IdUtil.simpleUUID());
        jedis.zadd("OrderId", DateUtil.currentSeconds(), "O-" + IdUtil.simpleUUID());
        jedis.zadd("OrderId", DateUtil.currentSeconds() - 60L, "O-" + IdUtil.simpleUUID());
        jedis.zadd("OrderId", DateUtil.currentSeconds() + 120L, "O-" + IdUtil.simpleUUID());
        jedis.zadd("OrderId", DateUtil.currentSeconds() - 30L, "O-" + IdUtil.simpleUUID());

        Set<Tuple> scores = jedis.zrangeWithScores("OrderId", 0, 0);
        scores.forEach(e -> {
            String orderId = e.getElement();
            System.out.println("1消费的订单OrderId为: " + orderId);
        });

        scores = jedis.zrangeWithScores("OrderId", 2, 4);
        scores.forEach(e -> {
            String orderId = e.getElement();
            System.out.println("2消费的订单OrderId为: " + orderId);
        });


        scores = jedis.zrangeWithScores("OrderId", 0, 100);
        scores.forEach(e -> {
            String orderId = e.getElement();
            Long num = jedis.zrem("OrderId", orderId);
            if (num != null && num > 0) {
                System.out.println(System.currentTimeMillis() + "ms:redis消费了一个任务：消费的订单OrderId为" + orderId);
            }
        });

//        scores = jedis.zrangeWithScores("OrderId", 0, 10);
//        System.out.println("scores.size(): " + scores.size());
//
//        Thread.sleep(1000);
//
//        scores.forEach(e -> {
//            boolean b = AppTest2.consumerDelayMessage(e);
//            if (b) {
//                String orderId = e.getElement();
//                Long num = jedis.zrem("OrderId", orderId);
//                if (num != null && num > 0) {
//                    System.out.println(System.currentTimeMillis() + "ms:redis消费了一个任务：消费的订单OrderId为" + orderId);
//                }
//            }
//        });
//
//
//        jedis.zadd("OrderId", DateUtil.currentSeconds(), "O-" + IdUtil.simpleUUID());
//        jedis.zadd("OrderId", DateUtil.currentSeconds(), "O-" + IdUtil.simpleUUID());
//        jedis.zadd("OrderId", DateUtil.currentSeconds(), "O-" + IdUtil.simpleUUID());
//
//        scores = jedis.zrangeWithScores("OrderId", 0, 10);
//        System.out.println("scores.size(): " + scores.size());
//
//        Thread.sleep(1000);
//
//        scores.forEach(e -> {
//            boolean b = AppTest2.consumerDelayMessage(e);
//            if (b) {
//                String orderId = e.getElement();
//                Long num = jedis.zrem("OrderId", orderId);
//                if (num != null && num > 0) {
//                    System.out.println(System.currentTimeMillis() + "ms:redis消费了一个任务：消费的订单OrderId为" + orderId);
//                }
//            }
//        });
//
//        Thread.sleep(1000);
//
//        scores = jedis.zrangeWithScores("OrderId", 0, 10);
//        System.out.println("scores.size(): " + scores.size());
//
//        scores.forEach(e -> {
//            String orderId = e.getElement();
//            Long num = jedis.zrem("OrderId", orderId);
//            if (num != null && num > 0) {
//                System.out.println(System.currentTimeMillis() + "ms:redis消费了一个任务：消费的订单OrderId为" + orderId);
//            }
//        });
    }
}
