package com.mudxx.demo.boot.message.web.api;

import com.mudxx.common.exception.code.biz.BizErrorCode;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.common.web.response.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/6 10:25
 */
@RestController
@RequestMapping("/api/message/mail")
public class SimpleMailController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("test")
    @ResponseBody
    private CommonResult<?> test(){
        throw new BizException(BizErrorCode.BIZ_ERROR);
//        return CommonResult.success();
    }

    @GetMapping("send")
    @ResponseBody
    private CommonResult<?> send(){
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人
        message.setFrom("15868178044@163.com");
        // 收件人
        message.setTo("15868178044@163.com");
        // 邮件标题
        message.setSubject("邮箱发送");
        // 邮件内容
        message.setText("这是一条用于测试Spring Boot邮件发送功能的邮件！哈哈哈~~~");
        // 抄送人
        message.setCc("xxx@qq.com");
        mailSender.send(message);
        return CommonResult.success();
    }
}
