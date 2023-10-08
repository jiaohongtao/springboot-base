package com.hong.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * cron表达式生成工具类
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月04日
 */
public class CronUtil {

    /***
     * 生成指定格式日期字符
     *
     * @param date 日期
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return formatTimeStr
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        dateFormat = dateFormat == null ? "yyyy-MM-dd HH:mm:ss" : dateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return date != null ? sdf.format(date) : null;
    }

    /**
     * 生成cron表达式 ss mm HH dd MM ? yyyy
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     *
     * @param date : 时间点
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
