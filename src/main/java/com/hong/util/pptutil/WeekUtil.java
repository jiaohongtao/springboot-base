package com.hong.util.pptutil;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Result;
import com.hong.exception.BusinessException;
import com.hong.util.httpRequest.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static Result generateWeek() throws BusinessException {

        String path;
        try {
            // File file = new File(WEEK_CONTENT_DIR + "WeekAll.txt");
            File file = new File(WEEK_CONTENT_DIR + "WeekAll2.txt");
            // Map<String, String> contentMap = RenderPowerPointTemplate.weekContentRead(file);
            // 使用 === 分割
            Map<String, String> contentMap = RenderPowerPointTemplate.weekContentRead2(file);
            InputStream inputStream = new FileInputStream(WEEK_CONTENT_DIR + "周报Template.pptx");

            path = RenderPowerPointTemplate.renderPowerPointTemplate(inputStream, contentMap, WEEK_CONTENT_DIR);

        } catch (IOException e) {
            log.error("发送失败", e);
            return Result.failed("生成失败");
        }
        return Result.success(path);
    }

    public static Result generateWeekAndSend() throws BusinessException {
        Result result = generateWeek();
        if (result.isFailed() || Objects.isNull(result.getData())) {
            return result;
        }
        // File file = new File(result.getData().toString());

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
        // weekGet();
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


    public static String weekGet() {
        // Bearer a6950ebf-3e78-47c5-a4be-38a9d7f052be
        // http://10.1.3.150:8000/prod-api/auth/login
        String url = "http://10.1.3.150:8000/prod-api/system/link/show";
        String authorization = "Bearer a6950ebf-3e78-47c5-a4be-38a9d7f052be";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);
        String result = HttpClientUtil.httpGetAndHeader(url, null, headers);
        System.out.println(result);

        return "";
    }

    public static String login() {
        // http://10.1.3.150:8000/prod-api/auth/login
        String url = "http://10.1.3.150:8000/prod-api/auth/login";
        JSONObject body = new JSONObject();
        body.put("code", "1");
        body.put("password", "mima212014a");
        body.put("username", "jiaohongtao");

        String uuid = "d59c1d35e41c476991073f769f9ab5be";
        System.out.println(uuid);

        body.put("uuid", uuid);

        /*for (int i = 0; i < 100; i++) {

        }*/
        String result = HttpClientUtil.httpPostRaw(url, null, body);
        System.out.println(result);

        return "";
    }

}
