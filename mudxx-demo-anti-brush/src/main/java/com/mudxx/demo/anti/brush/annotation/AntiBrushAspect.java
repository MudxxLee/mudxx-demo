package com.mudxx.demo.anti.brush.annotation;


import cn.hutool.core.util.StrUtil;
import com.mudxx.common.exceptiion.code.biz.BizException;
import com.mudxx.common.web.util.WebUtils;
import com.mudxx.component.redis.StringRedisUtils;
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
 * @author laiw
 * @date 2023/3/31 13:33
 */
@Aspect
@Component
public class AntiBrushAspect {

    @Autowired
    private StringRedisUtils stringRedisUtils;

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     */
    @Pointcut("@annotation(com.mudxx.demo.anti.brush.annotation.AntiBrush)")
    public void limitPointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("limitPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();
        // 获取方法上的注解
        AntiBrush antiBrush = method.getAnnotation(AntiBrush.class);
        if (antiBrush == null) {
            // 如果没有注解，则继续调用，不做任何处理
            return pjp.proceed();
        }
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求IP地址
        String ip = WebUtils.getIpAddress(request);
        // 请求url路径
        String uri = request.getRequestURI();
        // 存到redis中的key
        final String redisKey = StrUtil.join(":", AntiBrushConstants.DEFAULT_PREFIX_KEY, antiBrush.partKey(), ip, uri);
        // 缓存中存在key，在限定访问周期内已经调用过当前接口
        if (stringRedisUtils.hasKey(redisKey)) {
            // 访问次数自增1
            stringRedisUtils.increment(redisKey, 1L);
            System.out.println(StrUtil.format("redisKey={} value={}", redisKey, stringRedisUtils.get(redisKey)));
            // 超出访问次数限制
            if ( Long.parseLong(stringRedisUtils.get(redisKey)) > antiBrush.count()) {
                throw new BizException(antiBrush.msg());
            }
            // 未超出访问次数限制，不进行任何操作，返回true
        } else {
            // 第一次设置数据,过期时间为注解确定的访问周期
            stringRedisUtils.set(redisKey, "1", antiBrush.cycle());
        }
        return pjp.proceed();
    }


}
