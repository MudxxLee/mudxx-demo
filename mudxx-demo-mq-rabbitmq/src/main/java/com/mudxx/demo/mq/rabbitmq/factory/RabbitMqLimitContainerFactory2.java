package com.mudxx.demo.mq.rabbitmq.factory;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author laiw
 * @date 2023/7/3 14:23
 */
@Component
public class RabbitMqLimitContainerFactory2 {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Bean(name = "limitContainerFactory2")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        // ack模式
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置消息，每批最多获取3个
        factory.setPrefetchCount(1);
        // 是否自动启动消息的监听 默认为true
        factory.setAutoStartup(false);
        return factory;
    }


}
