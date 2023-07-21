package com.mudxx.demo.mq.rabbitmq.producer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author laiwen
 */

@Configuration
@EnableScheduling
public class RabbitMqSender {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    private final static String RABBITMQ_QUEUE = "Agilewing-LR-POC";

    @Autowired
    private RabbitTemplate rabbitTemplate;


//    @PostConstruct
    //@Scheduled(cron = "0/10 * * * * ?")
    public void send() {
        System.out.println("test send msg start");
        try {
            for (int i = 0; i < 10; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("time", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                map.put("desc", "测试");
                rabbitTemplate.convertAndSend(RABBITMQ_QUEUE, JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));
            }
            System.out.println("test send msg end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @PostConstruct
    public void send2() {
        System.out.println("test send msg start");
        try {
            for (int i = 0; i < 1; i++) {
                //创建消费对象，并指定消息主键
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setMessageId("1122334455667");
                byte[] body = "{\"A\":\"1\"}".getBytes(StandardCharsets.UTF_8);
                Message message = new Message(body, messageProperties);
                rabbitTemplate.convertAndSend(RABBITMQ_QUEUE, message);
            }
            System.out.println("test send msg end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
