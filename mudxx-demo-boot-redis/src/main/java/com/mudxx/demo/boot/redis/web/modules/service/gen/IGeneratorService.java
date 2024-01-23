package com.mudxx.demo.boot.redis.web.modules.service.gen;

public interface IGeneratorService {

    /**
     * 生成业务唯一号(自增方式)
     * redis key = {{bizPrefix}}:{{MD5（bizCondition）}}
     * redis value = 自增+1
     * redis expire = 过期设定30天有效期
     * 出参：{{bizPrefix}}{{redis自增返回值（按规则填充数字）}}
     *
     * @param bizPrefix       业务前缀 必需
     * @param bizCondition    业务条件 必需
     * @param bizSuffixLength 业务后缀长度 可选 范围：[1-9],若自增结果超出长度范围,按自增结果返回
     * @param bizSuffixPadStr 业务后缀填充符号 可选 规则：仅支持0-9的单个数字,不传默认按0填充
     * @return 业务唯一号
     */
    String genAndIncrUniqueNo(String bizPrefix, String bizCondition, Integer bizSuffixLength, String bizSuffixPadStr);

}
