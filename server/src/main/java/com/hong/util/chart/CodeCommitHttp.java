package com.hong.util.chart;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeCommitHttp {
    public static String getCode() {
        // 请求URL和参数
        String url = "http://10.1.3.150:8000/prod-api/data/gitCode/list";
        String requestBody = "{\"orderBy\":\"CODE_COMMIT\",\"gitUsername\":\"韩振\",\"params\":{\"beginTime\":\"2023-01-03\",\"endTime\":\"2024-01-03\"}}";

        // 创建HttpClient对象
        HttpClient httpClient = HttpClients.createDefault();

        // 创建请求参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("pageNum", "1"));
        params.add(new BasicNameValuePair("pageSize", "1000"));

        // 创建URI对象，并将请求参数添加到URI中
        URI uri;
        try {
            uri = new URIBuilder(url).addParameters(params).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }

        // 创建HttpPost请求对象
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Accept", "application/json, text/plain, */*");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.setHeader("Authorization", "Bearer 92551fe0-0796-4a84-8621-99ca6fa79a2a");
        httpPost.setHeader("Cache-Control", "no-cache");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Cookie", "rememberMe=true; username=jiaohongtao; password=Lkep6TPDpHpf88bDasAbU5+auNYHI4sUoKNdBsBFXVzXsScIjKpm2joG57tWbHYArzLHqrNCF5SmNCsQeSctnA==; Admin-Token=92551fe0-0796-4a84-8621-99ca6fa79a2a; Admin-Expires-In=43200");
        httpPost.setHeader("Origin", "http://10.1.3.150:8000");
        httpPost.setHeader("Pragma", "no-cache");
        httpPost.setHeader("Referer", "http://10.1.3.150:8000/data/code");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

        // 设置请求体
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

        // 发送请求并获取响应
        HttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        // 处理响应
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String responseBody;
        try {
            responseBody = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        // System.out.println("Status code: " + statusCode);
        // System.out.println("Response body: " + responseBody);

        return responseBody;
    }

    public static List<JSONObject> getSimpleCode(String req) {
        JSONObject json = JSONObject.parseObject(req);
        // System.out.println(json.getJSONArray("rows"));

        // 从每个Map对象中取出指定的字段组成一个新的Map，然后将多个Map组成一个新的List集合

        return json.getJSONArray("rows").toJavaList(JSONObject.class).stream()
                .filter(map -> map.getInteger("codeAddNum") < 1000).map(item -> {
            JSONObject newItem = new JSONObject();
            newItem.put("codeAddNum", item.get("codeAddNum"));  // 这里示例从每个Map对象中取出id和name字段
            newItem.put("codeCommitNum", item.get("codeCommitNum"));
            newItem.put("codeCommitDate", item.get("codeCommitDate"));
            return newItem;
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // getCode();
        getSimpleCode(getCode());
    }
}
