//package com.ismyself.testDmo.common.rabbitmq;
//
//import org.springframework.amqp.core.AcknowledgeMode;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * package com.ismyself.testDmo.common.rabbitmq;
// *
// * @auther txw
// * @create 2020-07-18  11:35
// * @description：
// */
//@Configuration
//public class RabbitConfig {
//
//    //rabbitmq 连接工厂实例
//    @Autowired
//    private CachingConnectionFactory connectionFactory;
//    //消息监听器所在的容器工厂配置类实例
//    @Autowired
//    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
//
//    @Bean(name = "simpleListenerContainer")
//    public SimpleRabbitListenerContainerFactory listenerContainer() {
//        //定义消息监听器所在的容器工厂
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        //设置工厂实例
//        factory.setConnectionFactory(connectionFactory);
//        //设置消息传输格式
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        //设置并发消费者实例的初始数量
//        factory.setConcurrentConsumers(1);
//        //设置消费者实例的最大数量
//        factory.setMaxConcurrentConsumers(10);
//        //设置消费者实例中每个实例拉取的消息数量
//        factory.setPrefetchCount(30);
//        return factory;
//    }
//
//    @Bean(name = "multiListenerContainer")
//    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
//        //定义消息监听器所在的容器工厂
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        //设置容器工厂所用的实例
//        factoryConfigurer.configure(factory, connectionFactory);
//        //设置消息传输格式
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        //设置消息的确认消费模式，NONE表示不需要确认
//        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
//        //设置并发消费者实例的初始数量
//        factory.setConcurrentConsumers(10);
//        //设置消费者实例的最大数量
//        factory.setMaxConcurrentConsumers(10);
//        //设置消费者实例中每个实例拉取的消息数量
//        factory.setPrefetchCount(30);
//        return factory;
//    }
//
//    @Bean(name = "rabbitTemplate")
//    public RabbitTemplate rabbitTemplate() {
//
//        //设置发送消息进行确认
//        connectionFactory.setPublisherConfirms(true);
//        //设置发送消息后返回确认消息
//        connectionFactory.setPublisherReturns(true);
//        //实例创建
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        //设置消息传输的格式为JSON
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        rabbitTemplate.setMandatory(true);
//        //设置发送消息发送成功
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> System.out.println("消息发送成功"));
//        //设置消息发送失败
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("消息丢失"));
//
//        return rabbitTemplate;
//
//    }
//
//
//}
