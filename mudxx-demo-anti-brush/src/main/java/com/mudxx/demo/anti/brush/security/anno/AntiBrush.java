package com.mudxx.demo.anti.brush.security.anno;

import java.lang.annotation.*;

/**
 * 用于防刷限流的注解
 * <p>
 * 针对同一请求ip、同一请求路径进行限流
 *
 * @author laiwen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AntiBrush {

    /**
     * key(固定前缀+自定义部分+固定后缀)
     */
    String partKey() default "";

    /**
     * 过期时长(秒)
     */
    long expireSecond() default 10L;

    /**
     * 最大并发请求数(即在 key 未过期时间内的最大请求数，超出则被拦截)
     */
    int maxCount() default 1000;

    /**
     * 默认提示信息
     */
    String msg() default "操作频繁，请稍后再试！";
}
