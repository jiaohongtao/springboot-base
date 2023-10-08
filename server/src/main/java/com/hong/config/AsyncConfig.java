package com.hong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 多线程配置
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年06月30日
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    /*
   此处成员变量应该使用@Value从配置中读取
    */
    private final int corePoolSize = 10;
    private final int maxPoolSize = 200;
    private final int queueCapacity = 10;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

}
