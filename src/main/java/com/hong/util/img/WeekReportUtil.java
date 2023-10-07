package com.hong.util.img;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Constant;
import com.hong.bean.dfjx.LoginReq;
import com.hong.bean.dfjx.WeekReport;
import com.hong.util.httpRequest.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

/**
 * 周报工具 TODO　未完善
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/28
 */
@Slf4j
public class WeekReportUtil {

    public static final String BASE_URL = "http://10.1.3.150:8000/prod-api";

    public static void autoSendDailyReport() {
        JSONObject codeUUID = getCodeUUID();
        String cookie = loginGetCookie(codeUUID.getString("code"), codeUUID.getString("uuid"));
        System.out.println(cookie);

        String result = weekReportSend(cookie);
        log.info(result);
    }

    public static void main(String[] args) {
        // autoSendDailyReport();
        String weekStartDate = getWeekStartDate();
    }

    public static JSONObject getCodeUUID() {
        String imgSavePath = Constant.DESKTOP_PATH + "\\image.jpg";

        JSONObject jsonImg = ImgUtil.getImgInfo();
        String imgBase64 = jsonImg.getString("img");
        String uuid = jsonImg.getString("uuid");
        ImgUtil.saveImgFile(imgBase64, imgSavePath);
        ImgUtil.openImg(imgSavePath);
        String scanResult = ImgUtil.getScan();
        JSONObject json = new JSONObject();
        json.put("code", scanResult);
        json.put("uuid", uuid);
        return json;
    }

    public static String loginGetCookie(String code, String uuid) {
        String url = BASE_URL + "/auth/login";
        LoginReq loginReq = LoginReq.builder().username("jiaohongtao").password("123456")
                .code(code).uuid(uuid)
                .build();

        String result = HttpClientUtil.httpPostData(url, null, loginReq);
        log.info(result);

        JSONObject json = JSONObject.parseObject(result);
        return json.getJSONObject("data").getString("access_token");
    }

    private static String getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // 格式化日期输出
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // System.out.printf("本周周一日期：%04d-%02d-%02d", year, month, day);
        return year + "-" + month + "-" + day;
    }

    public static String weekReportSend(String cookie) {
        String dailyUrl = BASE_URL + "/data/weekly";

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        log.info("Formatted Date: " + formattedDate);


        WeekReport weeklyReport = WeekReport.builder()
                .fileId(3627)
                //.weeklyStartTime("2022-09-26")
                // .weeklyEndTime("2022-09-30")
                .weeklySummary("1.sdp7.1&8.0组件编译调研zookeeper\n2.sdp7.1&8.0组件编译调研oozie\n3.sdp7.1&8.0组件编译调研tez\n4.sdp7.1&8.0组件编译调研sqoop\n5.编译文档总结\n")
                .weeklyTaskList(ListUtil.of(
                        WeekReport.WeekReportTask.builder()
                                .taskId(32)
                                .taskPlan("sdp7.1&8.0组件编译调研hdp-zookeeper\n")
                                .taskContent("1.sdp7.1&8.0组件编译调研zookeeper\n2.sdp7.1&8.0组件编译调研oozie\n3.sdp7.1&8.0组件编译调研tez\n4.sdp7.1&8.0组件编译调研sqoop\n5.编译文档总结\n")
                                .planTaskAmount(3)
                                .actualTaskAmount(3)
                                .documentAmount(1)
                                .trainingAmount(1)
                                .isDelay(0)
                                .deptId(200)
                                .taskType(0)
                                .build()
                ))
                .nextWeekPlan("sdp7.1&8.0组件编译调研hdp-zookeeper\n")
                .build();

        Map<String, String> header = Collections.singletonMap("Authorization", "Bearer " + cookie);
        return HttpClientUtil.httpPostData(dailyUrl, null, weeklyReport, header);
    }

}
