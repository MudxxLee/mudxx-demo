package com.mudxx.demo.anti.brush.web.api;


import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.anti.brush.security.anno.AntiBrush;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author laiw
 * @date 2023/3/31 14:03
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/1")
    @AntiBrush(partKey = "test001", expireSecond = 60L, maxCount = 5)
    public CommonResult<?> test001() {
        return CommonResult.success("请求成功!");
    }

}
