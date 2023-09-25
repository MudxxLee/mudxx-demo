package com.mudxx.demo.anti.brush.security.anno;


import cn.hutool.core.util.StrUtil;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.common.web.util.WebUtils;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切面类：请求限流
 *
 * @author laiw
 * @date 2023/3/31 13:33
 */
@Aspect
@Component
public class RequestIpFcyAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIpFcyAspect.class);

    @Autowired
    private StringRedisFcyHelper stringRedisFcyHelper;

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     */
    @Pointcut("@annotation(com.mudxx.demo.anti.brush.security.anno.RequestIpFcy)")
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
        RequestIpFcy methodAnnotation = method.getAnnotation(RequestIpFcy.class);
        //获取 类中是否包含注解，也就是controller 是否有注解
        RequestIpFcy classAnnotation = method.getDeclaringClass().getAnnotation(RequestIpFcy.class);
        // 如果 方法上有注解就优先选择方法上的参数，否则类上的参数
        RequestIpFcy requestIpFcy = ObjectUtils.defaultIfNull(methodAnnotation, classAnnotation);
        if (requestIpFcy == null) {
            // 如果没有注解，则继续调用，不做任何处理
            return pjp.proceed();
        }
        // 请求限流处理
        return this.doRequest(pjp, method, requestIpFcy);
    }

    /**
     * 请求限流处理
     */
    private Object doRequest(ProceedingJoinPoint pjp, Method method, RequestIpFcy requestIpFcy) throws Throwable {
        // Ip key
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        final String ipKey = this.buildRedisKey(method, requestIpFcy.suffixKey(), WebUtils.getIpAddress(request));
        // 轮询尝试(阻塞线程)
        boolean execute = stringRedisFcyHelper.retryIncrAndExpire(
                ipKey,
                requestIpFcy.ipExpireSecond(),
                requestIpFcy.ipMaxCount(),
                requestIpFcy.ipMaxWaitTime(),
                requestIpFcy.ipRetryIntervalMillis());
        if (!execute) {
            // 获取失败
            LOGGER.warn("请求IP限流，当前已超出最大并发请求限制");
            throw new BizException(requestIpFcy.msg());
        }

        // 并发 key
        final String fcyKey = this.buildRedisKey(method, requestIpFcy.suffixKey(), "fcy");
        // 轮询尝试(阻塞线程)
        boolean result = stringRedisFcyHelper.retryIncrAndExpire(
                fcyKey,
                requestIpFcy.fcyExpireSecond(),
                requestIpFcy.fcyMaxCount(),
                requestIpFcy.fcyMaxWaitTime(),
                requestIpFcy.fcyRetryIntervalMillis());
        if (!result) {
            // 释放Ip
            stringRedisFcyHelper.tryDecrAndExpire(ipKey, requestIpFcy.ipExpireSecond());
            // 获取失败
            LOGGER.warn("请求并发限流，当前已超出最大并发请求限制");
            throw new BizException(requestIpFcy.msg());
        }

        // 获取成功
        try {
            // 执行业务逻辑处理
            return pjp.proceed();
        } finally {
            // 释放Ip
            stringRedisFcyHelper.tryDecrAndExpire(ipKey, requestIpFcy.ipExpireSecond());
            // 释放并发
            stringRedisFcyHelper.tryDecrAndExpire(fcyKey, requestIpFcy.fcyExpireSecond());
        }
    }

    private String buildRedisKey(Method method, String suffixKey, String fixedKey) {
        if (StrUtil.isBlank(suffixKey)) {
            String methodName = method.getName();
            String classSimpleName = method.getDeclaringClass().getSimpleName();
            return StrUtil.join(":", RedisKeyConstants.DEFAULT_PREFIX_KEY, classSimpleName, methodName);
        }
        // 存到redis中的key
        return StrUtil.join(":", RedisKeyConstants.DEFAULT_PREFIX_KEY, suffixKey, fixedKey);
    }

}
