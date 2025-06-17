package com.zappcomments.zappcomments.commentservice.rabbitmq;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TEXT_PROCESSOR_SERVICE_POST_PROCESSING = "text-processor-service.post-processing.v1.e";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory){
        return new RabbitAdmin(factory);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(TEXT_PROCESSOR_SERVICE_POST_PROCESSING).build();
    }

}
