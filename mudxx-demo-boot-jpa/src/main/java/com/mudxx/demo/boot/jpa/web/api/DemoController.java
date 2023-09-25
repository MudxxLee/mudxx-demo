package com.mudxx.demo.boot.jpa.web.api;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransService;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/demo")
@RestController
public class DemoController {

    @Autowired
    private ITestTransDefService testTransDefService;
    @Autowired
    private ITestTransService testTransService;

    @GetMapping("1")
    public CommonResult<?> test1() {
        testTransDefService.test();
        return CommonResult.success("成功");
    }

    @GetMapping("001")
    public CommonResult<?> test001() {
        testTransService.test001();
        return CommonResult.success("成功");
    }

    @GetMapping("002")
    public CommonResult<?> test002() {
        testTransService.test002();
        return CommonResult.success("成功");
    }

    @GetMapping("003")
    public CommonResult<?> test003() {
        testTransService.test003();
        return CommonResult.success("成功");
    }
}


