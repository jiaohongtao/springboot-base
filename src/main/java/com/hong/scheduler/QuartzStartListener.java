package com.hong.scheduler;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 启动服务后，开启调度器
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月04日
 */
@Configuration
public class QuartzStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private QuartzSchedulerUtil quartzScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            quartzScheduler.startJob();
            System.out.println("*******quartz调度器启动*******");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
