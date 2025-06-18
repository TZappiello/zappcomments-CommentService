package com.zappcomments.zappcomments.commentservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TEXT_PROCESSOR_SERVICE_POST_PROCESSING = "text-processor-service.post-processing.v1.e";
    public static final String POST_PROCESSOR_SERVICE_POST_TRANSFORM = "post-service.post-processing-result.v1.q";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory){
        return new RabbitAdmin(factory);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(TEXT_PROCESSOR_SERVICE_POST_PROCESSING).build();
    }


    @Bean
    public Queue queuePostsTransform() {
        return QueueBuilder.durable(POST_PROCESSOR_SERVICE_POST_TRANSFORM).build();
    }

    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("post-service.post-processing-result.v1.e").build();
    }

    @Bean
    public Binding bindingPosts() {
        return BindingBuilder.bind(queuePostsTransform()).to(exchange());
    }

}
