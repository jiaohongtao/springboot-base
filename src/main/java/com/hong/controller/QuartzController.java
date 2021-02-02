package com.hong.controller;

import com.hong.bean.Result;
import com.hong.scheduler.CronUtil;
import com.hong.scheduler.QuartzSchedulerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月04日
 */
@RestController
@Api("调度器")
public class QuartzController {

    @Autowired
    QuartzSchedulerUtil quartzScheduler;

    @GetMapping("/addQuartz")
    @ApiOperation(value = "添加任务", httpMethod = "GET")
    public Result addQuartz() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse("2020-11-04 18:00:00");
            quartzScheduler.add(1, CronUtil.getCron(date));
            return Result.success("调用添加成功");
        } catch (SchedulerException | ParseException e) {
            e.printStackTrace();
            return Result.failed("调用添加失败");
        }
    }

    @GetMapping("/removeQuartz")
    @ApiOperation(value = "添加任务", httpMethod = "GET")
    public Result removeQuartz() {
        try {
            quartzScheduler.remove(1);
            return Result.success("调用移除成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.failed("调用移除失败");
        }
    }
}
