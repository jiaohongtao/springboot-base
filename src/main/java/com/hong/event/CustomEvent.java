package com.hong.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 * https://blog.csdn.net/qq_37687594/article/details/113200974
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/07
 */
public class CustomEvent extends ApplicationEvent {

    public static final long serialVersionUID = 1L;

    private String msg;

    public CustomEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
