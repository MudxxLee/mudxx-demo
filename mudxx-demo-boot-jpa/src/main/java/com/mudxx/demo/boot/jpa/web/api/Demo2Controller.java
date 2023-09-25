package com.mudxx.demo.boot.jpa.web.api;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.jpa.modules.table.service.IPgwTestTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/demo2")
@RestController
public class Demo2Controller {

    @Autowired
    private IPgwTestTempService pgwTestTempService;

    @GetMapping("/get/{id}")
    public CommonResult<?> get(@PathVariable("id") String id) {
        return CommonResult.success(pgwTestTempService.findById(id));
    }

    @GetMapping("/list")
    public CommonResult<?> list() {
        return CommonResult.success(pgwTestTempService.findList());
    }
}


