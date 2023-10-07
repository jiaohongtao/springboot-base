package com.hong.config;

import com.hong.filter.FilterGlobalTraceId;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GlobalWebTraceIdConfig {
    @Bean
    public FilterRegistrationBean<FilterGlobalTraceId> loggingFilter() {
        FilterRegistrationBean<FilterGlobalTraceId> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FilterGlobalTraceId());
        // 设置过滤的URL模式
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}