package com.mudxx.demo.anti.brush.security.anno;


import cn.hutool.core.util.StrUtil;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.common.web.util.WebUtils;
import com.mudxx.component.redis.lock.spring.StringRedisFcyHelper;
import com.mudxx.demo.anti.brush.config.RedisKeyConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切面类：实现限流校验
 *
 * @author laiw
 * @date 2023/3/31 13:33
 */
@Aspect
@Component
public class AntiBrushAspect {

    @Autowired
    private StringRedisFcyHelper stringRedisFcyHelper;

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     */
    @Pointcut("@annotation(com.mudxx.demo.anti.brush.security.anno.AntiBrush)")
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
        // 获取方法上的注解
        AntiBrush antiBrush = method.getAnnotation(AntiBrush.class);
        if (antiBrush != null) {
            //获取request对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            // 校验请求是否超过限定值
            this.doRequest(request, antiBrush);
        }
        return pjp.proceed();
    }

    /**
     * 请求限流处理
     */
    private void doRequest(HttpServletRequest request, AntiBrush antiBrush) {
        final String redisKey = this.buildRedisKey(request, antiBrush.partKey());
        if (!stringRedisFcyHelper.tryIncrAndExpire(redisKey, antiBrush.expireSecond(), antiBrush.maxCount())) {
            throw new BizException(antiBrush.msg());
        }
    }

    private String buildRedisKey(HttpServletRequest request, String partKey) {
        // 获取请求IP地址
        String ip = WebUtils.getIpAddress(request);
        // 请求url路径
        String uri = request.getRequestURI();
        // 存到redis中的key
        return StrUtil.join(":", RedisKeyConstants.DEFAULT_PREFIX_KEY, partKey, ip, uri);
    }

}
