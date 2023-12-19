package com.mudxx.demo.boot.jpa.web.api.sys;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.jpa.modules.table.service.ISysDictDataService;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataSaveDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataUpdateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataUpdateStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典数据
 *
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/sys/dict/data")
@RestController
public class SysDictDataController {

    @Autowired
    private ISysDictDataService sysDictDataService;

    @GetMapping("/list/{sysDictCodeId}")
    public CommonResult<?> list(@PathVariable String sysDictCodeId) {
        return CommonResult.success(sysDictDataService.findBySysDictCodeId(sysDictCodeId, false));
    }

    @GetMapping("/cache")
    public CommonResult<?> cache() {
        return CommonResult.success(sysDictDataService.findAllUsableGroupData());
    }

    @PostMapping("/save/{sysDictCodeId}")
    public CommonResult<?> save(@PathVariable String sysDictCodeId, @RequestBody SysDictDataSaveDto saveDto) {
        saveDto.setSysDictCodeId(sysDictCodeId);
        sysDictDataService.saveData(saveDto, "System");
        return CommonResult.success();
    }

    @PostMapping("/update/{id}")
    public CommonResult<?> update(@PathVariable String id, @RequestBody SysDictDataUpdateDto updateDto) {
        updateDto.setId(id);
        sysDictDataService.updateData(updateDto, "System");
        return CommonResult.success();
    }

    @PostMapping("/update-state/{id}")
    public CommonResult<?> updateState(@PathVariable String id, @RequestBody SysDictDataUpdateStateDto updateStateDto) {
        updateStateDto.setId(id);
        sysDictDataService.updateState(updateStateDto, "System");
        return CommonResult.success();
    }
}


