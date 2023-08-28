package com.hong.middleware.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "01-boot",    //获取 msg 的主题,对应msg所在的主题
        consumerGroup = "wolfcode-consumer"        //消费者分组
)
public class CustomRocketmqListener implements RocketMQListener<MessageExt> {
    /**
     * 消费者会一直监听指定主题下面的消息,如果消息来了就执行 onMessage 方法
     * 参数是消息内容
     *
     * @param msg 消息内容
     */
    @Override
    public void onMessage(MessageExt msg) {
        log.info("消费消息" + msg);
        log.info("===============");
        log.info("body : {}", new String(msg.getBody()));
    }
}
