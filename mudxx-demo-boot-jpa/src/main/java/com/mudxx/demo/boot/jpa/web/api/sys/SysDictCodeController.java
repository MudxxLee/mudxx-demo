package com.mudxx.demo.boot.jpa.web.api.sys;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.jpa.modules.table.service.ISysDictCodeService;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeSaveDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeUpdateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeUpdateStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/sys/dict/code")
@RestController
public class SysDictCodeController {

    @Autowired
    private ISysDictCodeService sysDictCodeService;

    @GetMapping("/lazy")
    public CommonResult<?> list(@RequestParam(required = false) String pid) {
        return CommonResult.success(sysDictCodeService.lazyLoading(pid));
    }

    @PostMapping("/save")
    public CommonResult<?> save(@RequestBody SysDictCodeSaveDto saveDto) {
        sysDictCodeService.saveCode(saveDto, "System");
        return CommonResult.success();
    }

    @PostMapping("/update/{id}")
    public CommonResult<?> update(@PathVariable String id, @RequestBody SysDictCodeUpdateDto updateDto) {
        updateDto.setId(id);
        sysDictCodeService.updateCode(updateDto, "System");
        return CommonResult.success();
    }

    @PostMapping("/update-state/{id}")
    public CommonResult<?> updateState(@PathVariable String id, @RequestBody SysDictCodeUpdateStateDto updateStateDto) {
        updateStateDto.setId(id);
        sysDictCodeService.updateState(updateStateDto, "System");
        return CommonResult.success();
    }
}


