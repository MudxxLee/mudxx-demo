package com.mudxx.demo.mq.rabbitmq.consumer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer2 {
    private static final String QUEUE_NAME = "Agilewing-LR-POC";
    private static final String HOST = "mq-dev.agilewingcdn-demo.com";
    private static final int PORT = 5672;
    private static final String USERNAME = "appuser";
    private static final String PASSWORD = "37UxMDvdPVfi4gTE9L";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost("/");


        // 创建连接
        Connection connection = factory.newConnection();

        // 创建信道
        Channel channel = connection.createChannel();

        // 设置每次只接收一条消息
        channel.basicQos(1);



        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonStr = new String(body, StandardCharsets.UTF_8);
                System.out.println("receive msg -> time: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + ", body:" + jsonStr);

                // 手动确认消息已被处理
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 创建定时任务调度器
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // 每隔固定时间拉取消息 每隔5秒执行一次
        executorService.scheduleAtFixedRate(() -> {
            try {
                // 拉取消息
                channel.basicConsume(QUEUE_NAME, false, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);

//        // 保持程序运行，直到手动停止
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // 关闭连接和信道
//        channel.close();
//        connection.close();
//
//        // 关闭定时任务调度器
//        executorService.shutdown();
    }
}
