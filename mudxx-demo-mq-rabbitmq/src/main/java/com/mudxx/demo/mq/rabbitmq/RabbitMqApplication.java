package com.mudxx.demo.mq.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laiw
 * @date 2023/4/3 10:24
 */
@SpringBootApplication(scanBasePackages = "com.mudxx")
public class RabbitMqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApplication.class, args);
    }
}
