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
public class RabbitMqLimitContainerFactory {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Bean(name = "limitContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        // ack模式
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置消息，每批最多获取3个
        factory.setPrefetchCount(2);
        return factory;
    }


}
