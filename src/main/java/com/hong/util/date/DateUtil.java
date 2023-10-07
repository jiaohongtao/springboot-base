package com.hong.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {
    public static void main(String[] args) {
        System.out.println(getWeekEndDate());
    }

    public static String getWeekEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate lastWorkingDay = today;

        // 获取本周的最后一天
        DayOfWeek lastDayOfWeek = today.getDayOfWeek();
        int daysUntilLastWorkingDay = 0;
        if (lastDayOfWeek == DayOfWeek.SATURDAY) {
            daysUntilLastWorkingDay = 1;
        } else if (lastDayOfWeek == DayOfWeek.SUNDAY) {
            daysUntilLastWorkingDay = 2;
        }

        lastWorkingDay = lastWorkingDay.minusDays(daysUntilLastWorkingDay);

        // 判断是否为工作日
        while (!isWorkingDay(lastWorkingDay)) {
            lastWorkingDay = lastWorkingDay.minusDays(1);
        }

        // 格式化日期输出
        int year = lastWorkingDay.getYear();
        int month = lastWorkingDay.getMonthValue();
        int day = lastWorkingDay.getDayOfMonth();
        // System.out.printf("本周工作日的最后一天：%04d-%02d-%02d", year, month, day);
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    // 判断日期是否为工作日
    private static boolean isWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday(date);
    }

    // 判断日期是否为法定节假日
    private static boolean isHoliday(LocalDate date) {
        // 判断法定节假日的具体逻辑，可以使用相关的法定节假日列表或接口查询
        // 在此处添加法定节假日判断的逻辑，如国庆节、春节、清明节等
        return false;
    }
}