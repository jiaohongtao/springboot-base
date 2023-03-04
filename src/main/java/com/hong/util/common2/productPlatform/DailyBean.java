package com.hong.util.common2.productPlatform;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2023-02-10 17:29:50
 *
 * @author json.cn (i@json.cn)
 */
@Data
public class DailyBean {

    private String createTime;
    private int dailyId;
    private Date dailyTime;
    private int standardTime;
    private String updateTime;
    private String userId;
    private List<DailyTaskList> dailyTaskList;
}