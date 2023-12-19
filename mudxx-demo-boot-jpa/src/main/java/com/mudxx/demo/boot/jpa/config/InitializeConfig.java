package com.mudxx.demo.boot.jpa.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mudxx.component.redis.utils.StringRedisUtils;
import com.mudxx.demo.boot.jpa.modules.table.model.SysDictDataGroupModel;
import com.mudxx.demo.boot.jpa.modules.table.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laiw
 * @date 2023/11/22 15:38
 */
@Configuration
public class InitializeConfig {

    @Autowired
    private StringRedisUtils stringRedisUtils;
    @Autowired
    private ISysDictDataService sysDictDataService;

    @PostConstruct
    public void initDict() {
        Map<String, List<SysDictDataGroupModel>> map = sysDictDataService.findAllUsableGroupData();
        Map<String, String> maps = new HashMap<>();
        for (Map.Entry<String, List<SysDictDataGroupModel>> entry : map.entrySet()) {
            maps.put(entry.getKey(), JSON.toJSONString(entry.getValue(), SerializerFeature.WriteMapNullValue));
        }
        final String redisKey = "DICT:DATA";
        stringRedisUtils.hPutAll(redisKey, maps, 60L * 60L);
    }

}
