package com.mudxx.demo.boot.web.api.cache;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.web.modules.service.cache.ICacheTestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/3 15:56
 */
@Api(tags = "缓存测试")
@RestController
@RequestMapping("/api/cache/test")
public class CacheTestController {

    @Autowired
    private ICacheTestService cacheTestService;

    @GetMapping("1")
    public CommonResult<?> test1(String id) {
        return CommonResult.success(cacheTestService.getEntity(id));
    }

    @GetMapping("2")
    public CommonResult<?> test2(String id) {
        cacheTestService.saveEntity(id);
        return CommonResult.success();
    }

    @GetMapping("3")
    public CommonResult<?> test3(String id) {
        cacheTestService.deleteEntity(id);
        return CommonResult.success();
    }

}
