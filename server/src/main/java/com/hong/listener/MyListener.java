package com.hong.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年07月07日
 */
@Component
public class MyListener implements ApplicationListener<ApplicationEvent> {

    /**
     * 当容器中发布此事件以后，方法触发
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("收到事件：" + applicationEvent);
    }
}
