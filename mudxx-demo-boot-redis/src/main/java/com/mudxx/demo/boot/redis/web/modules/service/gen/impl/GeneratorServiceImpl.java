package com.mudxx.demo.boot.redis.web.modules.service.gen.impl;

import com.mudxx.common.exception.code.CommonErrorCode;
import com.mudxx.common.exception.code.biz.BizErrorCode;
import com.mudxx.common.exception.code.biz.ParameterErrorCode;
import com.mudxx.common.exception.utils.VUtils;
import com.mudxx.common.utils.crypt.md5.Md5Util;
import com.mudxx.component.redis.lock.script.RedisLuaScript;
import com.mudxx.component.redis.utils.StringRedisUtils;
import com.mudxx.demo.boot.redis.web.modules.service.gen.IGeneratorService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author laiw
 * @date 2024/1/19 14:08
 */
@Service
public class GeneratorServiceImpl implements IGeneratorService {

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Override
    public String genAndIncrUniqueNo(String bizPrefix, String bizCondition, Integer bizSuffixLength, String bizSuffixPadStr) {
        VUtils.isBlank(bizPrefix).throwMessage(ParameterErrorCode.NULL_ERROR, "bizPrefix");
        VUtils.isBlank(bizCondition).throwMessage(ParameterErrorCode.NULL_ERROR, "bizCondition");
        if (bizSuffixLength != null) {
            VUtils.isFalse(bizSuffixLength > 0 && bizSuffixLength <= 9)
                    .throwMessage(ParameterErrorCode.NOT_IN_RANGE, "bizSuffixLength", "0-9");
            VUtils.isBlank(bizSuffixPadStr).throwMessage(ParameterErrorCode.NULL_ERROR, "bizSuffixPadStr");
            VUtils.isFalse(Pattern.matches("[0-9]", bizSuffixPadStr))
                    .throwMessage(ParameterErrorCode.NOT_IN_RANGE, "bizSuffixPadStr", "0-9");
        }
        //
        final String redisKey = String.join(":", "GEN", bizPrefix, Md5Util.md5(bizCondition));
        final Long increment = stringRedisUtils.increment(redisKey);
        stringRedisUtils.setExpire(redisKey, 30, TimeUnit.DAYS);





        int suffixLength = ObjectUtils.defaultIfNull(bizSuffixLength, 0);
        String suffixPadStr = StringUtils.defaultIfBlank(bizSuffixPadStr, "0");
        return String.join("", bizPrefix, StringUtils.leftPad(increment.toString(), suffixLength, suffixPadStr));
    }

    private Long tryIncrAndExpire(StringRedisUtils stringRedisUtils,
                                  final String bizKey,
                                  final long bizExpireSecond,
                                  final String bakKey) {
        return stringRedisUtils.getStringRedisTemplate().execute(
                RedisLuaScript.INCR_BAK_REDIS_SCRIPT,
                Stream.of(bizKey, bakKey).collect(Collectors.toList()),
                String.valueOf(bizExpireSecond));
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.leftPad("1231514", 10));
        System.out.println(StringUtils.leftPad("1231514", 5));
        System.out.println(StringUtils.leftPad("1231514", 0));
        System.out.println(StringUtils.leftPad("1231514", 10, null));
    }

}
