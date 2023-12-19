package com.mudxx.demo.boot.redis;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.shiro.session.Session;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author laiw
 * @date 2023/10/7 11:18
 */
@Slf4j
public class AppTest5 {


    private static final JedisPool jedisPool = new JedisPool(
            new GenericObjectPoolConfig(),
            "r-gs532h5yt0vzvgtt02.redis.singapore.rds.aliyuncs.com",
            6379,
            6000,
            null,
            3);

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void main(String[] args) {
        Jedis jedis = AppTest5.getJedis();
        String preKey = "shiro:cache:e96d756f-db3a-4e2f-9af8-7f3a3369138b";
        byte[] bytes = jedis.get(preKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(bytes);
        Session s = (Session)ShiroRedisSerializeUtils.deserialize(bytes);
        System.out.println(JSONUtil.toJsonStr(s.getAttributeKeys()));
    }

}
