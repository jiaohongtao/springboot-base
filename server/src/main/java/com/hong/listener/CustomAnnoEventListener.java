package com.hong.listener;

import com.hong.event.CustomEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 注解监听
 * 这个类由于使用注解监听，也可以被叫做统一事件的监听处理，因为再加一个监听的话，创建一个事件类 A，在这里监听上，其他处调用时使用事件A，这里就监听并处理了
 * https://blog.csdn.net/qq_37687594/article/details/113200974
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/07
 */
@Component
public class CustomAnnoEventListener {

    public static final Logger logger = LoggerFactory.getLogger(CustomAnnoEventListener.class);

    @EventListener
    public void listenerEvent(CustomEvent event) {
        logger.info("收到监听事件消息：{}", event.getMsg());
    }
}
