package com.mudxx.demo.boot.jpa.web.api.sys;

import com.alibaba.fastjson.JSON;
import com.mudxx.common.web.response.CommonResult;
import com.mudxx.component.redis.utils.StringRedisUtils;
import com.mudxx.demo.boot.jpa.modules.table.model.SysDictDataGroupModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/sys/dict/cache")
@RestController
public class SysDictCacheController {

    private static final String REDIS_KEY = "DICT:DATA";

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @GetMapping("/get")
    public CommonResult<?> cache(@RequestParam(name = "code", required = false) String code) {
        if (StringUtils.isNotBlank(code)) {
            String value = (String) stringRedisUtils.hGet(REDIS_KEY, code);
            List<SysDictDataGroupModel> ret = JSON.parseArray(value, SysDictDataGroupModel.class);
            return CommonResult.success(ret);
        } else {
            Map<Object, Object> map = stringRedisUtils.hGetAll(REDIS_KEY);
            Map<String, List<SysDictDataGroupModel>> ret = new HashMap<>();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                ret.put((String) entry.getKey(), JSON.parseArray((String) entry.getValue(), SysDictDataGroupModel.class));
            }
            return CommonResult.success(ret);
        }
    }

}


