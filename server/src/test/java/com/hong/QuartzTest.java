package com.hong;

import com.hong.scheduler.CronUtil;
import com.hong.scheduler.QuartzSchedulerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月04日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuartzTest {

    @Autowired
    QuartzSchedulerUtil quartzScheduler;
    @Test
    public void test1() {
        // Date date = new Date();
        // String time = CronUtil.formatDateByPattern(date, null);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-11-04 17:25:30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            quartzScheduler.add(1, CronUtil.getCron(date));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
