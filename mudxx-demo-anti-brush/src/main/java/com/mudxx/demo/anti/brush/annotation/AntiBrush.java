package com.mudxx.demo.anti.brush.annotation;

import java.lang.annotation.*;

/**
 * 用于防刷限流的注解
 * @author laiwen
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AntiBrush {

    /** 限流的部分key */
    String partKey() default "";

    /** 周期,单位是秒 */
    long cycle() default AntiBrushConstants.DEFAULT_CYCLE;

    /** 请求次数 */
    long count() default AntiBrushConstants.DEFAULT_COUNT;

    /** 默认提示信息 */
    String msg() default AntiBrushConstants.DEFAULT_MSG;
}
