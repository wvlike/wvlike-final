package com.ismyself.testDmo.common.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * package com.ismyself.testDmo.common.rabbitmq;
 *
 * @auther txw
 * @create 2020-07-11
 * @description：
 */
@Configuration
public class MyRabbitMqConfig {

    public static final String WVLIKE_QUEUE = "wvlike_demo_queue";
    public static final String WVLIKE_TOPIC = "wvlike_topic_exchange";
    public static final String ROUTINGKEY = "wvlike_routingKey";


    @Bean
    public Queue myDemoQueue() {
        return new Queue(WVLIKE_QUEUE);
    }

    @Bean
    public Exchange myDemoExchange() {
        return new TopicExchange(WVLIKE_TOPIC);
    }

    @Bean
    public Binding MyBinding(Queue myDemoQueue, Exchange myDemoExchange) {
        return BindingBuilder.bind(myDemoQueue).to(myDemoExchange).with(ROUTINGKEY).noargs();
    }

}
