package com.hong.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 调度器使用
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月04日
 */
@Slf4j
// @Component
@Configuration
public class QuartzSchedulerUtil {

    private static final String JOB_NAME = "inspect_report";
    private static final String JOB_GROUP = "inspect_report_group";
    private static final String TRIGGER_NAME = "inspect_report";
    private static final String TRIGGER_GROUP = "inspect_report_group";
    private static final String JOB_TASK_ID = "job_task_id";

    /**
     * quartz任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 开始执行所有任务，并开启调度器
     *
     * @throws SchedulerException SchedulerException
     */
    public void startJob() throws SchedulerException {
        scheduler.start();
    }

    public void add(int i, String cron) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class).withIdentity(JOB_NAME + i, JOB_GROUP).build();
        // 每5s执行一次
        // String cron = "*/5 * * * * ?";
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().
                withIdentity(TRIGGER_NAME + i, TRIGGER_GROUP).withSchedule(scheduleBuilder).build();
        cronTrigger.getJobDataMap().put(JOB_TASK_ID, i);
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public void remove(int i) throws SchedulerException {
        boolean deleteJob = scheduler.deleteJob(new JobKey(JOB_NAME + i, JOB_GROUP));
        log.info(deleteJob ? "任务移除成功" : "任务移除失败");
    }

    /**
     * 初始注入scheduler
     *
     * @return scheduler
     * @throws SchedulerException SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }

}
