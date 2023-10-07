package com.hong.bean.dfjx;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class WeekReport {

    private String createTime;
    private int fileId;
    private String nextWeekPlan;
    private String updateTime;
    private String userId;
    private Date weeklyEndTime;
    private String weeklyId;
    private Date weeklyStartTime;
    private String weeklySummary;
    private String weeklyProblemsRisks;
    private List<WeekReportTask> weeklyTaskList;

    @Data
    @Builder
    public static class WeekReportTask {

        private int planTaskAmount;
        private int actualTaskAmount;
        private int documentAmount;
        private int trainingAmount;
        private String taskContent;
        private String taskPlan;
        private int deptId;
        private int taskType;
        private int isDelay;
        private int taskId;
    }
}

