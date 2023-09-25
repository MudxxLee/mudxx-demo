package com.mudxx.demo.boot.redis.utils;

import com.mudxx.demo.boot.redis.web.modules.service.cahce.IRedisCacheService;

/**
 * @author laiw
 * @date 2023/8/10 09:23
 */
public class SpringTest {

    public void aVoid() {
        IRedisCacheService cacheService = SpringContextUtils.getBean(IRedisCacheService.class);
        cacheService.getStr();
    }

    public static void aVoid1() {
        IRedisCacheService cacheService = SpringContextStaticUtils.getBean(IRedisCacheService.class);
        cacheService.getStr();
    }

}
