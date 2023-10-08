package com.hong.controller.event;

import com.hong.publish.CustomPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事件控制器
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/07
 */
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private CustomPublisher customPublisher;

    @GetMapping
    public void event() {
        customPublisher.publish("请求发布事件");
    }
}
