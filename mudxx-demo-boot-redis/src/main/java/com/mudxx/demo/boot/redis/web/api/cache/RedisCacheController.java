package com.mudxx.demo.boot.redis.web.api.cache;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.redis.utils.SpringTest;
import com.mudxx.demo.boot.redis.web.modules.service.cahce.IRedisCacheService;
import com.mudxx.demo.boot.redis.web.modules.service.gen.IGeneratorService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author laiw
 * @date 2023/4/3 15:56
 */
@Api(tags = "缓存测试")
@RestController
@RequestMapping("/api/redis/cache")
public class RedisCacheController {

    @Autowired
    private IRedisCacheService redisCacheService;
    @Autowired
    private IGeneratorService generatorService;

    @GetMapping("1")
    public CommonResult<?> test1(String id) {
        return CommonResult.success(redisCacheService.getEntity(id));
    }

    @GetMapping("2")
    public CommonResult<?> test2(String id) {
        redisCacheService.saveEntity(id);
        return CommonResult.success();
    }

    @GetMapping("3")
    public CommonResult<?> test3(String id) {
        redisCacheService.deleteEntity(id);
        return CommonResult.success();
    }

    @GetMapping("4")
    public CommonResult<?> test4() {
        new SpringTest().aVoid();
        SpringTest.aVoid1();
        return CommonResult.success();
    }

    @GetMapping("set")
    public CommonResult<?> set() {
        redisCacheService.set();
        return CommonResult.success();
    }

    @GetMapping("get")
    public CommonResult<?> get() {
        redisCacheService.get();
        return CommonResult.success();
    }

    @GetMapping("sets")
    public CommonResult<?> sets() {
        redisCacheService.setStr();
        return CommonResult.success();
    }

    @GetMapping("gets")
    public CommonResult<?> gets() {
        redisCacheService.getStr();
        return CommonResult.success();
    }

    @PostMapping("gen")
    public CommonResult<?> gen(@RequestBody Map<String, Object> body) {
        String uniqueNo = generatorService.genAndIncrUniqueNo(
                MapUtils.getString(body, "bizPrefix"),
                MapUtils.getString(body, "bizCondition"),
                MapUtils.getInteger(body, "bizSuffixLength"),
                MapUtils.getString(body, "bizSuffixPadStr")
        );
        return CommonResult.success(uniqueNo);
    }
}
