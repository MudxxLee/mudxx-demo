package com.mudxx.demo.anti.brush.web.api;


import com.mudxx.common.utils.random.RandomUtil;
import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.anti.brush.security.anno.RequestFcy;
import com.mudxx.demo.anti.brush.security.anno.RequestIpFcy;
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
@RequestMapping("/api/fcy")
public class FcyController {

    @GetMapping("/1")
    @RequestFcy(suffixKey = "", expireSecond = 300L, maxCount = 5, maxWaitTime = 6L, retryIntervalMillis = 100L)
    public CommonResult<?> test001() throws InterruptedException {
        String random = RandomUtil.random(1, "12345");
        System.out.println(String.format("[线程 %s] sleep %s", Thread.currentThread().getName(), random));
        Thread.sleep(Integer.parseInt(random) * 1000L);
        return CommonResult.success("请求成功!");
    }

    @GetMapping("/2")
    @RequestIpFcy(suffixKey = "", ipExpireSecond = 60L, ipMaxCount = 2, ipMaxWaitTime = 5L, ipRetryIntervalMillis = 100L,
            fcyExpireSecond = 300L, fcyMaxCount = 5, fcyMaxWaitTime = 10L, fcyRetryIntervalMillis = 100L)
    public CommonResult<?> test002() throws InterruptedException {
        String random = RandomUtil.random(1, "12345");
        System.out.println(String.format("[线程 %s] sleep %s", Thread.currentThread().getName(), random));
        Thread.sleep(Integer.parseInt(random) * 1000L);
        return CommonResult.success("请求成功!");
    }

}
