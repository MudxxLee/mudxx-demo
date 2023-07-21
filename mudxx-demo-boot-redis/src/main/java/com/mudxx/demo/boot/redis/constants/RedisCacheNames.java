package com.mudxx.demo.boot.redis.constants;

/**
 * 格式: 缓存名称#过期时间(秒)
 * 如: XXX#30
 *
 * @author laiw
 * @date 2023/7/13 11:18
 */
public class RedisCacheNames {

    public static final String PREFIX = "cache:";

    public static final String TEST_GET = PREFIX + "test:get#60";


}
