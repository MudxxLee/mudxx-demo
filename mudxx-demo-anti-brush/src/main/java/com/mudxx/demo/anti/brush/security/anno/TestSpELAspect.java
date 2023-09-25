package com.mudxx.demo.anti.brush.security.anno;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laiw
 * @date 2023/9/6 15:26
 */
@Aspect
@Component
@Order(2)
@Log4j2
public class TestSpELAspect {

    @Pointcut("@annotation(com.mudxx.demo.anti.brush.security.anno.TestSpEL)")
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
        TestSpEL testSpEL = method.getAnnotation(TestSpEL.class);
        if (testSpEL != null) {
            //通过SpEL获取接口参数对象属性值
            StandardEvaluationContext context = this.setContextVariables(pjp);
            String s = this.getElValue(testSpEL.model(), context);
            System.out.println(s);
        }
        System.out.println(new ObjectMapper().writeValueAsString(pjp.getArgs()));
        System.out.println(getFieldsName(pjp));
        return pjp.proceed();
    }

    private StandardEvaluationContext setContextVariables(JoinPoint point) {
        Object[] args = point.getArgs();
        StandardEvaluationContext context = new StandardEvaluationContext(args);
        if (args == null || args.length <= 0) {
            return context;
        }
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method targetMethod = methodSignature.getMethod();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer
                = new LocalVariableTableParameterNameDiscoverer();
        String[] parametersName = parameterNameDiscoverer.getParameterNames(targetMethod);
        if (parametersName == null) {
            return context;
        }
        for (int i = 0; i < args.length; i++) {
            context.setVariable(parametersName[i], args[i]);
        }
        return context;
    }

    /**
     * 通过key SEL表达式获取值
     */
    private String getElValue(String key, StandardEvaluationContext context) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(key);
        return exp.getValue(context, String.class);
    }

    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 参数值
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                // 获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = Class.forName(classType).getMethod(methodName, classes);
        // 参数名
        String[] parameterNames = pnd.getParameterNames(method);
        // 通过map封装参数和参数值
        Map<String, Object> paramMap = new HashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", Long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };

}
