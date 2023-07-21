package com.mudxx.demo.mq.rabbitmq.consumer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class RabbitMQConsumer3 {

    private static final String QUEUE_NAME = "Agilewing-LR-POC";

    /**
     * 每次拉取的消息批量大小
     */
    private static final int BATCH_SIZE = 1;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 每隔 5 秒拉取一次消息
     */
//    @Scheduled(fixedDelay = 5000)
    public void pullMessages() {
        for (int i = 0; i < BATCH_SIZE; i++) {
            this.receiveMessage();
        }
    }

    public void basicGetMessage() {
        long deliveryTag = 0L;
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        Channel channel = connectionFactory.createConnection().createChannel(true);
        try {
            GetResponse getResponse = channel.basicGet(QUEUE_NAME, false);
            if(getResponse == null) {
                System.out.println("receive3 msg -> 暂无消息");
                return;
            }
            deliveryTag = getResponse.getEnvelope().getDeliveryTag();
            String jsonStr = new String(getResponse.getBody(), StandardCharsets.UTF_8);
            System.out.println("receive3 msg -> time: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + ", body:" + jsonStr);
            System.out.println(deliveryTag);
            // 执行业务
            Thread.sleep(2000);

            channel.basicAck(deliveryTag, false);
        } catch (IOException | InterruptedException e) {
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void receiveMessage() {
        long deliveryTag = 0L;
        Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(true);
        try {
            Message message = rabbitTemplate.receive(QUEUE_NAME);
            if(message == null) {
                System.out.println("receive3 msg -> 暂无消息");
                return;
            }
            System.out.println(message.getMessageProperties().getHeaders());

            deliveryTag = message.getMessageProperties().getDeliveryTag();
            String jsonStr = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("receive3 msg -> time: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + ", body:" + jsonStr);
            System.out.println(deliveryTag);
            // 执行业务
            Thread.sleep(2000);

//            channel.basicAck(deliveryTag, false);
        } catch (InterruptedException e) {
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

