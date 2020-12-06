package com.ismyself.testDmo.common.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.ismyself.testDmo.pojo.UserPo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * package com.ismyself.testDmo.common.rabbitmq;
 *
 * @auther txw
 * @create 2020-07-11
 * @description：
 */
@Component
public class MyRabbitMqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMyMqMsg() throws Exception {
        UserPo userPo = new UserPo(1, "tujiang", "male", 24);
        System.out.println("mymq send start");
        Message message = MessageBuilder.withBody(JSON.toJSONString(userPo).getBytes("utf-8")).build();
        rabbitTemplate.convertAndSend(MyRabbitMqConfig.WVLIKE_TOPIC, MyRabbitMqConfig.ROUTINGKEY, message);
//        rabbitTemplate.convertAndSend(MyRabbitMqConfig.WVLIKE_TOPIC, MyRabbitMqConfig.ROUTINGKEY, JSON.toJSONString(userPo));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

