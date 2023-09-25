package com.mudxx.demo.anti.brush.security.anno;

import java.lang.annotation.*;

/**
 * 用于请求限流的注解
 * <p>
 * 针对同一Controller或同一Controller下的某个方法进行请求限流
 * <p>
 * 当某个请求执行完毕会释放
 *
 * @author laiwen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestFcy {
    /**
     * key(固定前缀+自定义后缀)
     */
    String suffixKey() default "";

    /**
     * 过期时长(秒，-1L：默认不过期，非 -1 时必须 > 0)
     * <p>
     * 推荐：未防止请求线程意外中断（未执行释放），建议设置过期时长
     */
    long expireSecond() default -1L;

    /**
     * 最大并发请求数(即 key 存在时的最大值，超出则被拦截)
     */
    int maxCount();

    /**
     * 轮询最大等待时间(秒)
     */
    long maxWaitTime();

    /**
     * 轮询重试间隔时间(毫秒)
     * <p>
     * 推荐：[50~500] 轮询次数[内存8G、CPU4核]： 50_5~6 100_4~5 200_2~4 500_1~2)
     */
    long retryIntervalMillis();

    /**
     * 默认提示信息
     */
    String msg() default "网络繁忙，请稍后再试！";
}
