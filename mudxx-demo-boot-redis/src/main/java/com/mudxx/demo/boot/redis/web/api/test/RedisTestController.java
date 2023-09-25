package com.mudxx.demo.boot.redis.web.api.test;

import com.mudxx.common.utils.json.JsonUtil;
import com.mudxx.common.web.response.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author laiw
 * @date 2023/4/3 15:56
 */
@Api(tags = "缓存测试")
@RestController
@RequestMapping("/api/redis/test")
public class RedisTestController {

    @PostMapping("1")
    public CommonResult<?> test1(@RequestBody UserDto userDto) {
        System.out.println(JsonUtil.toString(userDto));
        System.out.println(userDto.getExtInfo());
        return CommonResult.success();
    }

    @PostMapping("2")
    public CommonResult<?> test2(@RequestParam(name = "code", required = false) String code, @RequestBody String data) {
        System.out.println("----:" + code);
        System.out.println("----:" + data);
        return CommonResult.success();
    }

}
