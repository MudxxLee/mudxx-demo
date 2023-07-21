package com.mudxx.demo.boot.web.api.hippo4j;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.web.api.demo.vo.PersonVo;
import com.mudxx.demo.boot.web.modules.hippo4j.service.TestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/3 13:35
 */
@Api(tags = "hippo4j-api")
@RestController
@RequestMapping("/api/hippo4j")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("test")
    public CommonResult<PersonVo> test() {
        for (int i = 0; i < 50; i++) {
            testService.aVoid();
        }
        return CommonResult.success();
    }

}

