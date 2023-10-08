package com.hong.enums;

/**
 * Log module 类
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/08/30
 */
public enum JhtService {

    BASE("基础服务");

    private final String value;

    JhtService(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
