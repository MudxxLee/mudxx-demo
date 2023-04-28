package com.mudxx.demo.boot.web.api;

import com.mudxx.captcha.core.domain.dto.CaptchaVerifyDTO;
import com.mudxx.captcha.core.service.CaptchaService;
import com.mudxx.common.web.response.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后端二次校验接口示例
 * @author laiwen
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/login")
    public CommonResult<?> get(@RequestParam("captchaVerification") String captchaVerification) {
        CaptchaVerifyDTO verifyDTO = new CaptchaVerifyDTO();
        verifyDTO.setCaptchaVerification(captchaVerification);
        captchaService.verification(verifyDTO);
        return CommonResult.success();
    }

}
