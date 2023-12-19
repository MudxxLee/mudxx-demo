package com.mudxx.demo.boot.redis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author laiw
 * @date 2023/9/28 17:23
 */
public class Test1 {

    public static void main(String[] args) {
        System.out.println(DateUtil.currentSeconds());
        System.out.println(DateUtil.date().getTime() / 1000L);
        System.out.println(new Date().getTime() / 1000L);

        long l = 100000000000000L;
        double d = (double) l;
        System.out.println(d); // 输出结果为1.0E14

        long l2 = 100000000000000L;
        BigDecimal bd = new BigDecimal(String.valueOf(l2));
        double d2 = bd.doubleValue();
        System.out.println(d2); // 输出结果为1.0E14


        long num = Long.MAX_VALUE;
        double result = Double.longBitsToDouble(Double.doubleToLongBits(num));
        System.out.println(result);

        long num1 = Long.MAX_VALUE;
        double result1 = 0;
        try {
            result1 = Double.longBitsToDouble(Double.doubleToLongBits(num1));
        } catch (IllegalArgumentException e) {
            System.out.println("Can't convert long to double, out of range.");
        }
        System.out.println(result1);


        long number = Long.MAX_VALUE;
        double result2 = NumberUtil.toDouble(number);
        System.out.println(result2);

    }
}
