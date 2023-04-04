package com.mudxx.demo.boot.web.api;


import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.web.model.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description  用户接口类
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    /**
     * 查询用户
     * @param age 年龄
     * @return CommonResult<UserVo>
     */
    @GetMapping("/list")
    public CommonResult<UserVo> list(@RequestParam int age){
        UserVo user = new UserVo("test", age);
        return CommonResult.success(user);
    }


}


