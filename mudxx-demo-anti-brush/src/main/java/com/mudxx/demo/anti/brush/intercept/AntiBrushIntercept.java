package com.mudxx.demo.anti.brush.intercept;

import cn.hutool.core.util.StrUtil;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.common.web.util.WebUtils;
import com.mudxx.component.redis.StringRedisUtils;
import com.mudxx.demo.anti.brush.annotation.AntiBrush;
import com.mudxx.demo.anti.brush.annotation.AntiBrushConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 客户端API防刷拦截器
 *
 * @author laiw
 * @date 2023/4/28 16:24
 */
@Slf4j
@Component
public class AntiBrushIntercept extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //isAssignableFrom()判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口（isAssignableFrom()方法是判断是否为某个类的父类）
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //HandlerMethod 封装方法定义相关的信息,如类,方法,参数等
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 获取方法中是否包含注解
            AntiBrush methodAnnotation = method.getAnnotation(AntiBrush.class);
            //获取 类中是否包含注解，也就是controller 是否有注解
            AntiBrush classAnnotation = method.getDeclaringClass().getAnnotation(AntiBrush.class);
            // 如果 方法上有注解就优先选择方法上的参数，否则类上的参数
            AntiBrush antiBrush = methodAnnotation != null ? methodAnnotation : classAnnotation;
            if (antiBrush != null) {
                checkLimit(request, antiBrush);
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 校验请求是否超过限定值
     */
    public void checkLimit(HttpServletRequest request, AntiBrush antiBrush) {
        // 受限的redis 缓存key
        final String redisKey = this.builderDefaultKey(request, antiBrush.partKey());
        // 缓存中存在key，在限定访问周期内已经调用过当前接口
        if (stringRedisUtils.hasKey(redisKey)) {
            // 访问次数自增1
            Long increment = stringRedisUtils.increment(redisKey, 1L);
            System.out.println(StrUtil.format("redisKey={} value={}", redisKey, increment));
            // 超出访问次数限制
            if ( increment > antiBrush.count()) {
                throw new BizException(antiBrush.msg());
            }
            // 未超出访问次数限制，不进行任何操作，返回true
        } else {
            // 第一次设置数据,过期时间为注解确定的访问周期
            stringRedisUtils.set(redisKey, "1", antiBrush.cycle());
        }
    }

    private String builderDefaultKey(HttpServletRequest request, String partKey) {
        // 获取请求IP地址
        String ip = WebUtils.getIpAddress(request);
        // 请求url路径
        String uri = request.getRequestURI();
        // 存到redis中的key
        return StrUtil.join(":", AntiBrushConstants.DEFAULT_PREFIX_KEY, partKey, ip, uri);
    }

}