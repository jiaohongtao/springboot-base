package com.hong.service_util.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DirectReceiver {

    @RabbitHandler
    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    public void process(Map<String, Object> testMessage) {
        log.info("DirectReceiver消费者收到消息  : {}" + testMessage.toString());
        Object messageData = testMessage.get("messageData");
        log.info("messageData是：{}", messageData);
    }

}