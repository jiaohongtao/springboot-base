package com.hong.util.common2;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Result;
import com.hong.exception.BusinessException;
import com.hong.util.common2.productPlatform.week.WeekBean;
import com.hong.util.httpRequest.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/10/21
 */
@Slf4j
public class WeekUtil {

    public static final String WEEK_CONTENT_DIR = "C:\\Users\\jx\\Desktop\\WeekContent\\";

    public static final String AUTHORIZATION_VALUE = "7f44d5f5-3ca5-4d88-8f1e-c6f47e372743";

    public static Result generateWeek() throws BusinessException {

        String path;
        try {
            File file = new File(WEEK_CONTENT_DIR + "WeekAll.txt");
            Map<String, String> contentMap = RenderPowerPointTemplate.weekContentRead(file);
            InputStream inputStream = new FileInputStream(WEEK_CONTENT_DIR + "周报Template.pptx");

            path = RenderPowerPointTemplate.renderPowerPointTemplate(inputStream, contentMap, WEEK_CONTENT_DIR);

        } catch (IOException e) {
            log.error("发送失败", e);
            return Result.failed("生成失败");
        }
        return Result.success(path);
    }

    public static Result sendWeekReport(File file) {
        // http://10.1.3.150:8000/prod-api/auth/login
        String url = "http://10.1.3.150:8000/prod-api/data/file/upload";
        JSONObject body = new JSONObject();
        body.put("fileType", "1");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
        String result = HttpClientUtil.sendPost(url, body, file, header);

        return Result.success(result);
    }

    public static Result sendWeekContent() {
        String url = "http://10.1.3.150:8000/prod-api/data/weekly";

        String weekContentJson = "{\"fileId\":3627,\"nextWeekPlan\":\"sdp7.1&8.0组件编译调研hdp-zookeeper\\n\",\"weeklyEndTime\":\"2022-09-30\",\"weeklyStartTime\":\"2022-09-26\",\"weeklySummary\":\"1.sdp7.1&8.0组件编译调研zookeeper\\n2.sdp7.1&8.0组件编译调研oozie\\n3.sdp7.1&8.0组件编译调研tez\\n4.sdp7.1&8.0组件编译调研sqoop\\n5.编译文档总结\\n\",\"weeklyTaskList\":[{\"planTaskAmount\":3,\"actualTaskAmount\":3,\"documentAmount\":1,\"trainingAmount\":1,\"taskContent\":\"1.sdp7.1&8.0组件编译调研zookeeper\\n2.sdp7.1&8.0组件编译调研oozie\\n3.sdp7.1&8.0组件编译调研tez\\n4.sdp7.1&8.0组件编译调研sqoop\\n5.编译文档总结\\n\",\"taskPlan\":\"sdp7.1&8.0组件编译调研hdp-zookeeper\\n\",\"deptId\":200,\"taskType\":0,\"isDelay\":0,\"taskId\":32}]}";
        WeekBean weekBean = JSONObject.parseObject(weekContentJson, WeekBean.class);
        // TODO 修改文件ID
        weekBean.setFileId(3627);
        weekBean.setNextWeekPlan("");
        weekBean.setWeeklyEndTime(new Date());
        weekBean.setWeeklyStartTime(new Date());

        JSONObject body = new JSONObject();
        body.put("fileType", "1");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
        /*String result = HttpClientUtil.sendPost(url, body, file, header);

        return Result.success(result);*/
        return Result.success("");
    }

    public static Result generateWeekAndSend() throws BusinessException {
        Result result = generateWeek();
        if (result.isFailed() || Objects.isNull(result.getData())) {
            return result;
        }

        // 上传文件
        File file = new File(result.getData().toString());
        sendWeekReport(file);

        return Result.success("");
    }

    public static void main(String[] args) throws IOException {
        Result result = generateWeek();
        if (result.isSuccess()) {
            log.info("生成成功");
            // 打开文件夹，命令和参数在一个数组内
            String[] cmdDir = {"explorer.exe", (String) result.getData()};
            Runtime.getRuntime().exec(cmdDir);
        }

        // dailySend();

        // weekLogin();
        /*File file = new File("C:\\Users\\jx\\Desktop\\WeekContent\\焦洪涛-2023-2-23-海盒大数据云产品线 SDP周报.pptx");
        Result sendWeekResult = sendWeekReport(file);
        System.out.println(sendWeekResult.getData());
        System.out.println(sendWeekResult.getMessage());*/

    }


    public static String dailySend() {
        String dailyUrl = "http://10.1.3.150:8000/prod-api/data/daily";

        /*String body = "{\n" +
                "    \"createTime\": null,\n" +
                "    \"dailyId\": \"\",\n" +
                "    \"dailyTime\": \"2022-10-21\",\n" +
                "    \"standardTime\": 0,\n" +
                "    \"updateTime\": null,\n" +
                "    \"userId\": null,\n" +
                "    \"dailyTaskList\": [\n" +
                "        {\n" +
                "            \"deptId\": 200,\n" +
                "            \"taskContent\": \"1. sdt安装包编译与插件编写 100%\\n2. sdt插件安装文档 100%\\n\",\n" +
                "            \"workTime\": \"8\",\n" +
                "            \"taskProgress\": 1,\n" +
                "            \"taskType\": 0,\n" +
                "            \"taskId\": 32\n" +
                "        }\n" +
                "    ]\n" +
                "}";*/
//        JSONObject bodyJson = new JSONObject();
//        bodyJson.put("dailyTime", DateFormat.getDateInstance().format(new Date()));
//
//        List<JSONObject> dailyTaskList = new ArrayList<>();
//        JSONObject dailyTask = new JSONObject();
//        dailyTask.put("deptId", 200);
//        dailyTask.put("taskContent", "1. sdt安装包编译与插件编写 100%\n2. sdt插件安装文档 100%");
//        dailyTask.put("workTime", 8);
//        dailyTask.put("taskProgress", 1);
//        dailyTask.put("taskType", 0);
//        dailyTask.put("taskId", 32);
//        dailyTaskList.add(dailyTask);
//        bodyJson.put("dailyTaskList", dailyTaskList);
//
//        String result = HttpClientUtil.httpPostRaw(dailyUrl, null, bodyJson);
//        log.info(result);
//        return result;
        return null;
    }


    public static String weekLogin() {
        // http://10.1.3.150:8000/prod-api/auth/login
        String url = "http://10.1.3.150:8000/prod-api/auth/login";
        JSONObject body = new JSONObject();
        body.put("code", "1");
        body.put("password", "123456");
        body.put("username", "jiaohongtao");
        body.put("uuid", "819bf0da11724ad9b94fc6a691735c70");

        /*for (int i = 0; i < 100; i++) {

        }*/
        String result = HttpClientUtil.httpPostRaw(url, null, body);
        System.out.println(result);

        return "";
    }

}
