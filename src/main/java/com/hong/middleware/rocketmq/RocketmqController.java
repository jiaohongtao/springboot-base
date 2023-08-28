package com.hong.middleware.rocketmq;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class RocketmqController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("01-hello")
    public String sendMsg(String message) {
        /*
         * 第一个参数 : topic ":" tag，主题后面是标签
         * 第二个参数 : message 消息内容
         */
        //底层是对 原生API 的封装, msg不需要转换成byte[],底层转
        // syncSend 同步请求 ,其他的就是方法名的改变
        SendResult sendResult = rocketMQTemplate.syncSend("01-boot:", message);
        log.info("发送ID: {}", sendResult.getMsgId());
        log.info("发送状态: {}", sendResult.getSendStatus());
        return "success";
    }

}
