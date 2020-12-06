package com.ismyself.testDmo.common.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * package com.ismyself.testDmo.common.rabbitmq;
 *
 * @auther txw
 * @create 2020-07-11  11:58
 * @description：
 */
@Component
public class MyRabbitMqListener {

    @RabbitHandler
    @RabbitListener(queues = MyRabbitMqConfig.WVLIKE_QUEUE)
    public void handlerMyDemoMsg(String msg) {
        System.out.println(msg);
    }
}
