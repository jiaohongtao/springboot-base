package com.hong.util.common2.productPlatform;

import com.alibaba.fastjson.JSONObject;
import com.hong.test.Baidu.Base64Util;
import com.hong.test.Baidu.FileUtil;
import com.hong.test.Baidu.LicensePlateRecognition;
import com.hong.util.httpRequest.HttpClientUtil;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/02/10
 */
public class DailyReport {

    public static final String URL = "http://10.1.3.150:8000/prod-api/data/daily";
    public static final String AUTHORIZATION_VALUE = "724be8d8-b88d-46f7-be46-69c036ce0039";
    public static final String URL_PRE = "http://10.1.3.150:8000/prod-api/data";

    public static Map<String, String> staticHeader = new HashMap<>();

    public DailyReport() {
        staticHeader.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
    }

    public static void main(String[] args) {

        // getCode();

        try {
            downLoadByUrl("http://10.1.3.150:8000/prod-api/code", "AAA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 删除，不可用，没有权限
    // String id = "19562";
    //        Map<String, String> header = new HashMap<>();
    //        header.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
    //        String result = HttpClientUtil.httpDeleteRaw(URL + "/" + id, null, new JSONObject(), header);
    //        System.out.println(result);

    /**
     * 查看今天日报
     */
    public static boolean get() {
        // 今天日期
        String toddyString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        // /prod-api/data/daily/list?pageNum=1&pageSize=10&userId=&taskType=&searchValue=&taskId=&params[beginTime]=2023-02-13&params[endTime]=2023-02-13
        // 先查看今天日报是否填写，防止重复填写
        String url = "http://10.1.3.150:8000/prod-api/data/daily/list?pageNum=1&pageSize=10&userId=&taskType=&searchValue=&taskId=&params[beginTime]=2023-02-13&params[endTime]=2023-02-13";
        url = "http://10.1.3.150:8000/prod-api/data/daily/list?pageNum=1&pageSize=10" +
                "&params[beginTime]=" + toddyString + "&params[endTime]=" + toddyString + "";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
        String result = HttpClientUtil.httpGet(url, null, new JSONObject(), header);
        JSONObject jsonObject = JSONObject.parseObject(result);
        assert jsonObject != null;
        List<DailyBean> rows = jsonObject.getJSONArray("rows").toJavaList(DailyBean.class);
        System.out.println(result);
        if (rows.size() > 0) {
            System.out.println("今天日报已填写");
            return true;
        }
        return false;
    }

    public static void insertReport() {
        if (!get()) {

            // 新建日报
            // {"createTime":null,"dailyId":"","dailyTime":"2023-02-13","standardTime":0,"updateTime":null,"userId":null,"dailyTaskList":[{"deptId":200,"taskType":0,"taskId":32,"workTime":"8","taskProgress":0,"taskContent":"1"}]}
            String newReport = "{\"createTime\":null,\"dailyId\":\"\",\"dailyTime\":\"2023-02-13\",\"standardTime\":0,\"updateTime\":null,\"userId\":null,\"dailyTaskList\":[{\"deptId\":200,\"taskType\":0,\"taskId\":32,\"workTime\":\"8\",\"taskProgress\":0,\"taskContent\":\"1\"}]}";
            System.out.println(newReport);

            DailyBean dailyBean = JSONObject.parseObject(newReport, DailyBean.class);
            dailyBean.setDailyTime(new Date());
            DailyTaskList dailyTaskList = dailyBean.getDailyTaskList().get(0);
            dailyTaskList.setTaskContent("daily");

            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
            String result = HttpClientUtil.httpPostRaw(URL, null, JSONObject.parseObject(JSONObject.toJSONString(dailyBean)), header);
            System.out.println(result);
        }

    }

    /**
     * 更新日报
     */
    public static void updateReport() {

        String newJSON = "{\"createTime\":null,\"dailyId\":19345,\"dailyTime\":\"2023-02-03\",\"standardTime\":8,\"updateTime\":null," +
                "\"userId\":null,\"dailyTaskList\":[{\"searchValue\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null," +
                "\"updateTime\":\"2023-02-03 18:13:02\",\"remark\":null,\"beginTime\":null,\"endTime\":null,\"ids\":null,\"params\":{}," +
                "\"dailyTaskId\":20416,\"dailyId\":19345,\"userId\":184,\"deptId\":200,\"demandSource\":null,\"taskType\":0," +
                "\"taskTypeName\":null,\"taskId\":32,\"taskName\":null,\"taskNumber\":null,\"workTime\":8,\"taskContent\":\"3\"," +
                "\"taskProgress\":0,\"taskProgressName\":null}]}";

        String defaultJSON = "{\"createTime\":null,\"dailyId\":19345,\"dailyTime\":\"2023-02-03\",\"standardTime\":8," +
                "\"updateTime\":null,\"userId\":null}";

        // 更新日报
        String allJson = "{\"createTime\":null,\"dailyId\":19345,\"dailyTime\":\"2023-02-03\",\"standardTime\":8,\"updateTime\":null,\"userId\":null,\"dailyTaskList\":[{\"searchValue\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":\"2023-02-03 18:13:02\",\"remark\":null,\"beginTime\":null,\"endTime\":null,\"ids\":null,\"params\":{},\"dailyTaskId\":20416,\"dailyId\":19345,\"userId\":184,\"deptId\":200,\"demandSource\":null,\"taskType\":0,\"taskTypeName\":null,\"taskId\":32,\"taskName\":null,\"taskNumber\":null,\"workTime\":8,\"taskContent\":\"3\",\"taskProgress\":0,\"taskProgressName\":null}]}";
        DailyBean dailyBean = JSONObject.parseObject(allJson, DailyBean.class);
        DailyTaskList dailyTaskList = dailyBean.getDailyTaskList().get(0);
        dailyTaskList.setTaskContent("6");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + AUTHORIZATION_VALUE);
        String result = HttpClientUtil.httpPutRaw(URL, null, JSONObject.parseObject(JSONObject.toJSONString(dailyBean)), header);
        System.out.println(result);
    }

    public static void getCode() {
        // http://10.1.3.150:8000/prod-api/code
        String url = "http://10.1.3.150:8000/prod-api/code";
        String result = HttpClientUtil.httpGet(url, null, null);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String img = jsonObject.getString("img");
        /*byte[] bytes = img.getBytes(StandardCharsets.UTF_8);
        String imgStr = Base64Util.encode(bytes);
        System.out.println(imgStr);*/

        /*try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return ImageIO.read(bais);
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }*/

        String code = null;
        try {
            code = LicensePlateRecognition.getCode(img.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(code);
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @throws IOException
     */
    public static String downLoadByUrl(String urlStr, String fileName) throws IOException {
        java.net.URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //获取项目根目录地址
        String propertiesFile = System.getProperty("user.dir");
        System.out.println(propertiesFile);
        //文件保存位置
        File saveDir = new File(propertiesFile);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return propertiesFile + "/" + fileName;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
