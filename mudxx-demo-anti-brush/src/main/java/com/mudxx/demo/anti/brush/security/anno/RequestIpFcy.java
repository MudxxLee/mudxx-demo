package com.mudxx.demo.anti.brush.security.anno;

import java.lang.annotation.*;

/**
 * 用于请求限流的注解
 * <p>
 * 1、针对同一Controller或同一Controller下的某个方法进行请求限流
 * <p>
 * 2、针对同一请求ip进行限流
 * <p>
 * 当某个请求执行完毕会释放
 *
 * @author laiwen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestIpFcy {
    /**
     * key(固定前缀+自定义后缀)
     */
    String suffixKey() default "";

    /**
     * 同一请求ip过期值(秒)
     */
    long ipExpireSecond() default 60L;

    /**
     * 同一请求ip最大并发请求数(即 key 存在时的最大值，超出则被拦截)
     */
    int ipMaxCount() default 100;

    /**
     * 轮询最大等待时间(秒)
     */
    long ipMaxWaitTime() default 10L;

    /**
     * 轮询重试间隔时间(毫秒)
     * <p>
     * 推荐：[50~500] 轮询次数[内存8G、CPU4核]： 50_5~6 100_4~5 200_3~4 500_1~2)
     */
    long ipRetryIntervalMillis() default 100L;

    /**
     * 并发过期时长(秒)
     */
    long fcyExpireSecond() default 300L;

    /**
     * 并发最大请求数(即 key 存在时的最大值，超出则被拦截)
     */
    int fcyMaxCount() default 1000;

    /**
     * 轮询最大等待时间(秒)
     */
    long fcyMaxWaitTime() default 10L;

    /**
     * 轮询重试间隔时间(毫秒)
     * <p>
     * 推荐：[50~500] 轮询次数[内存8G、CPU4核]： 50_5~6 100_4~5 200_3~4 500_1~2)
     */
    long fcyRetryIntervalMillis() default 200L;

    /**
     * 默认提示信息
     */
    String msg() default "网络繁忙，请稍后再试！";
}
