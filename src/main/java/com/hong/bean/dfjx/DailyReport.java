package com.hong.bean.dfjx;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2023-09-28 16:32:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
@Builder
public class DailyReport {

    private String createTime;
    private String dailyId;
    private String dailyTime;
    private int standardTime;
    private String updateTime;
    private String userId;
    private List<DailyReportTask> dailyTaskList;

    @Data
    @Builder
    public static class DailyReportTask {

        private int deptId;
        private int taskType;
        private int taskId;
        private String workTime;
        private int taskProgress;
        private String taskContent;
    }
}