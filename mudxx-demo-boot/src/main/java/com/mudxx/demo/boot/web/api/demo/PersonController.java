package com.mudxx.demo.boot.web.api.demo;

import com.mudxx.common.exception.code.biz.BizErrorCode;
import com.mudxx.common.web.response.CommonPage;
import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.web.api.demo.param.PersonForm;
import com.mudxx.demo.boot.web.api.demo.param.PersonQuery;
import com.mudxx.demo.boot.web.api.demo.vo.PersonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laiw
 * @date 2023/4/3 13:35
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query", required = true),
            @ApiImplicitParam(name = "age", value = "用户年龄", paramType = "query", example = "12")
    })
    @GetMapping("get")
    public CommonResult<PersonVo> getPerson(@RequestParam String name, @RequestParam int age) {
        return CommonResult.success(new PersonVo(name, age));
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("add")
    public CommonResult<PersonVo> addPerson(@RequestBody PersonForm personForm){
        if(StringUtils.isEmpty(personForm)){
            return CommonResult.failed(BizErrorCode.BIZ_ERROR);
        }
        return CommonResult.success(new PersonVo(personForm.getName(), personForm.getAge()));
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("page")
    public CommonPage<PersonVo> page(PersonQuery personQuery){
        List<PersonVo> list = new ArrayList<>();
        return CommonPage.restPage(list);
    }

}

