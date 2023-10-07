package com.hong.util.img;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Constant;
import com.hong.bean.dfjx.DailyReport;
import com.hong.bean.dfjx.LoginReq;
import com.hong.util.httpRequest.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

/**
 * 日报工具
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/28
 */
@Slf4j
public class DailyReportUtil {

    //登录form-data
    //{
    //    "username": "jiaohongtao",
    //    "password": "mima212014",
    //    "code": "8",
    //    "uuid": "525fcd7405c34e8c91a00c8d5edf53f1"
    //}
    public static final String BASE_URL = "http://10.1.3.150:8000/prod-api";

    public static void autoSendDailyReport() {
        JSONObject codeUUID = getCodeUUID();
        String cookie = loginGetCookie(codeUUID.getString("code"), codeUUID.getString("uuid"));
        System.out.println(cookie);

        String result = dailyReportSend(cookie);
        log.info(result);
    }

    public static void main(String[] args) {
        autoSendDailyReport();
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

    public static String dailyReportSend(String cookie) {
        String dailyUrl = BASE_URL + "/data/daily";

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        log.info("Formatted Date: " + formattedDate);

        DailyReport dailyTask = DailyReport.builder()
                .dailyTime(formattedDate)
                .standardTime(0)
                // .dailyId("")
                // .userId(null)
                // .createTime(null)
                // .updateTime(null)
                .dailyTaskList(ListUtil.of(
                        DailyReport.DailyReportTask.builder()
                                .deptId(200)
                                .taskType(0)
                                .taskId(32)
                                .workTime("8")
                                .taskProgress(0)
                                .taskContent("开发")
                                .build()
                ))
                .build();

        Map<String, String> header = Collections.singletonMap("Authorization", "Bearer " + cookie);
        return HttpClientUtil.httpPostData(dailyUrl, null, dailyTask, header);
    }

}
