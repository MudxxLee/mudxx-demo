package com.mudxx.demo.mq.rabbitmq.api;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mudxx.common.web.response.CommonResult;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laiw
 * @date 2023/7/3 14:30
 */
@RestController
@RequestMapping("/api/mq/send")
public class RabbitMqSendApi {

    private final static String RABBITMQ_QUEUE = "Agilewing-LR-POC1111";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/1")
    @ResponseBody
    public CommonResult<?> test1() {
        System.out.println("test send msg start");
        try {
            for (int i = 0; i < 1; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("time", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                map.put("desc", "测试");

                MessageProperties messageProperties = new MessageProperties();
                Message message = new Message(JSON.toJSONString(map, SerializerFeature.WriteMapNullValue).getBytes(StandardCharsets.UTF_8), messageProperties);
                rabbitTemplate.send(RABBITMQ_QUEUE, message);
//                rabbitTemplate.convertAndSend(RABBITMQ_QUEUE, JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));
            }
            System.out.println("test send msg end");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success();
    }

}
