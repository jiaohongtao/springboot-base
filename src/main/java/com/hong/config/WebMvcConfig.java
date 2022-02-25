package com.hong.config;

import com.hong.listener.AccessLimitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/02/25
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /*@Autowired
    private AccessLimitInterceptor accessLimitInterceptor;*/

    @Bean
    public AccessLimitInterceptor tokenVerifyInterceptor() {
        return new AccessLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("添加【AccessLimitInterceptor】拦截");
        // 两种
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/**");
        // registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**");
    }
}
