package com.hong.publish;

import com.hong.event.CustomEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 自定义发布
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/07
 */
@Component
public class CustomPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(String msg) {
        applicationEventPublisher.publishEvent(new CustomEvent(this, msg));
    }
}
