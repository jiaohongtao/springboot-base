package com.hong.service_util.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Rabbitmq 构建回调
 * https://yaoyao.blog.csdn.net/article/details/127082871
 **/
@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("ConfirmCallback:     " + "相关数据：" + correlationData);
                log.info("ConfirmCallback:     " + "确认情况：" + ack);
                log.info("ConfirmCallback:     " + "原因：" + cause);
            }
        });

        /*rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     " + "消息：" + message);
                System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
                System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
                System.out.println("ReturnCallback:     " + "交换机：" + exchange);
                System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
            }
        });*/

        // 会调用的情况之一：消息是推送到了交换机成功了，但是在路由分发给队列的时候，找不到队列
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(@NotNull ReturnedMessage returnedMessage) {
                log.info("ReturnCallback:     " + "消息：" + returnedMessage.getMessage());
                log.info("ReturnCallback:     " + "回应码：" + returnedMessage.getReplyCode());
                log.info("ReturnCallback:     " + "回应信息：" + returnedMessage.getReplyText());
                log.info("ReturnCallback:     " + "交换机：" + returnedMessage.getExchange());
                log.info("ReturnCallback:     " + "路由键：" + returnedMessage.getRoutingKey());
            }
        });

        return rabbitTemplate;
    }

}
