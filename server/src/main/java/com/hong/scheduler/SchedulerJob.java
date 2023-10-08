package com.hong.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月04日
 */
@Slf4j
public class SchedulerJob implements Job {
    private void before() {
        System.out.println("任务开始执行");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        before();
        log.info(new Date() + ":我开始执行了......");
        // TODO 业务
        after();
    }

    private void after() {
        System.out.println("任务结束执行");
    }
}
