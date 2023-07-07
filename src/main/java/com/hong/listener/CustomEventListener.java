package com.hong.listener;

import com.hong.event.CustomEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 自定义事件监听
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/07
 */
@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    public static final Logger logger = LoggerFactory.getLogger(CustomEventListener.class);

    @Override
    public void onApplicationEvent(CustomEvent event) {
        logger.info("监听到事件：{}", event.getMsg());
    }
}
