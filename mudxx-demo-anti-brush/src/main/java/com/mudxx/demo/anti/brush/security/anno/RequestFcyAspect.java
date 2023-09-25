package com.mudxx.demo.anti.brush.security.anno;


import cn.hutool.core.util.StrUtil;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.component.redis.lock.spring.StringRedisFcyHelper;
import com.mudxx.demo.anti.brush.config.RedisKeyConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面类：请求限流
 *
 * @author laiw
 * @date 2023/3/31 13:33
 */
@Aspect
@Component
public class RequestFcyAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFcyAspect.class);

    @Autowired
    private StringRedisFcyHelper stringRedisFcyHelper;

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     */
    @Pointcut("@annotation(com.mudxx.demo.anti.brush.security.anno.RequestFcy)")
    public void pointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();
        // 获取方法中是否包含注解
        RequestFcy methodAnnotation = method.getAnnotation(RequestFcy.class);
        //获取 类中是否包含注解，也就是controller 是否有注解
        RequestFcy classAnnotation = method.getDeclaringClass().getAnnotation(RequestFcy.class);
        // 如果 方法上有注解就优先选择方法上的参数，否则类上的参数
        RequestFcy requestFcy = ObjectUtils.defaultIfNull(methodAnnotation, classAnnotation);
        if (requestFcy == null) {
            // 如果没有注解，则继续调用，不做任何处理
            return pjp.proceed();
        }
        // 请求限流处理
        return this.doRequest(pjp, method, requestFcy);
    }

    /**
     * 请求限流处理
     */
    private Object doRequest(ProceedingJoinPoint pjp, Method method, RequestFcy requestFcy) throws Throwable {
        // 是否设置为不过期
        boolean never = requestFcy.expireSecond() == -1L;
        // 存到redis中的key
        final String redisKey = this.buildRedisKey(method, requestFcy.suffixKey());
        // 轮询尝试(阻塞线程)
        boolean result;
        if (never) {
            result = stringRedisFcyHelper.retryIncr(redisKey, requestFcy.maxCount(),
                    requestFcy.maxWaitTime(), requestFcy.retryIntervalMillis());
        } else {
            result = stringRedisFcyHelper.retryIncrAndExpire(redisKey, requestFcy.expireSecond(), requestFcy.maxCount(),
                    requestFcy.maxWaitTime(), requestFcy.retryIntervalMillis());
        }
        if (!result) {
            // 获取失败
            LOGGER.warn("请求限流，当前已超出最大并发请求限制");
            throw new BizException(requestFcy.msg());
        }
        // 获取成功
        try {
            // 执行业务逻辑处理
            return pjp.proceed();
        } finally {
            // 释放
            if (never) {
                stringRedisFcyHelper.tryDecr(redisKey);
            } else {
                stringRedisFcyHelper.tryDecrAndExpire(redisKey, requestFcy.expireSecond());
            }
        }
    }

    private String buildRedisKey(Method method, String suffixKey) {
        if (StrUtil.isBlank(suffixKey)) {
            String methodName = method.getName();
            String classSimpleName = method.getDeclaringClass().getSimpleName();
            return StrUtil.join(":", RedisKeyConstants.DEFAULT_PREFIX_KEY, classSimpleName, methodName);
        }
        // 存到redis中的key
        return StrUtil.join(":", RedisKeyConstants.DEFAULT_PREFIX_KEY, suffixKey);
    }

}
