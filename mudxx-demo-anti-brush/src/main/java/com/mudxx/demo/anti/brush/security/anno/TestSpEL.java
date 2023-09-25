package com.mudxx.demo.anti.brush.security.anno;


import java.lang.annotation.*;

/**
 * @author laiwen
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestSpEL {

    String model() default "";

}
