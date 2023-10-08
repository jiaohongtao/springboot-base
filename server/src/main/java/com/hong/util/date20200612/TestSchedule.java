package com.hong.util.date20200612;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 测试定时器    涉及注解@EnableScheduling @Scheduled @Component
 * <p>
 * href: https://www.cnblogs.com/chenpi/p/9785953.html
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年06月29日
 */
@Slf4j
@Component
@Async
public class TestSchedule {

    private final Logger logger = LoggerFactory.getLogger(TestSchedule.class);

    // @Scheduled(initialDelay = 1000, fixedRate = 10000)
    // @Scheduled(cron = "0/5 * * * * *")
    public void run() {
        System.out.println(Thread.currentThread().getName());
        logger.info("Current time is run:: " + Calendar.getInstance().getTime());
        System.out.println("====================");
    }

    // @Scheduled(cron = "0/6 * * * * *")
    public void run1() {
        System.out.println(Thread.currentThread().getName());
        logger.info("Current time is run1:: " + Calendar.getInstance().getTime());
    }

    // @Scheduled(cron = "0/2 * * * * *")
    public void run2() {
        System.out.println(Thread.currentThread().getName());
        logger.info("Current time is run2:: " + Calendar.getInstance().getTime());
    }

}
