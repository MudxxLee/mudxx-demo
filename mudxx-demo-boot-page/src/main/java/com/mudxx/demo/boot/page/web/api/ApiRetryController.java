package com.mudxx.demo.boot.page.web.api;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.page.modules.services.IRetryTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2024/2/4 17:37
 */
@RestController
@RequestMapping("/api/retry")
public class ApiRetryController {

    @Autowired
    private IRetryTestService retryTestService;

    @GetMapping("/1")
    public CommonResult<?> test001(@RequestParam("code") int code) throws Exception {
        retryTestService.retryServiceOne(code);
        return CommonResult.success("请求成功!");
    }

}
