package com.mudxx.demo.mq.rabbitmq.consumer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 如果消息类型为字节数组，则content-type为application/octet-stream；
 * 如果消息类型为字符串，则content-type为text/plain;
 * 如果消息类型为序列化对象，则content-type为application/x-java-serialized-object。
 *
 * @author laiw
 * @date 2022/9/6
 */
@Component
public class RabbitMqConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @RabbitListener(queues = "Agilewing-LR-POC")
    public void handler1(com.rabbitmq.client.Channel channel, org.springframework.amqp.core.Message message) throws Exception {
        String jsonStr = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("receive msg -> time: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + ", body:" + jsonStr);
        // 执行业务
        Thread.sleep(2000);
        // 手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    @RabbitListener(queues = "Agilewing-LR-POC", containerFactory = "limitContainerFactory")
    public void handler2(com.rabbitmq.client.Channel channel, org.springframework.amqp.core.Message message) throws Exception {
        String jsonStr = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("receive msg -> time: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + ", body:" + jsonStr);
        // 执行业务
        Thread.sleep(1000);
        // 手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    @RabbitListener(queues = "Agilewing-LR-POC")
    public void handler3(com.rabbitmq.client.Channel channel, org.springframework.amqp.core.Message message) throws Exception {
        String jsonStr = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("receive test msg -> time: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + ", body:" + jsonStr);
        // 拒绝消息
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("111111111");
        // 手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("22222222");
    }

}
