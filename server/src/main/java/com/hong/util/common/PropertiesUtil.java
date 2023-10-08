package com.hong.util.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月26日
 */
@Component
@ConfigurationProperties(prefix = "my")
public class PropertiesUtil {
    private String name;

    public void getName() {
        System.out.println(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertiesUtil() {
    }

    public PropertiesUtil(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        PropertiesUtil util = new PropertiesUtil();
        util.getName();
    }
}
