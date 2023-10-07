package com.hong.test.sdp.freeipa.user.origin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class UserModifyPasswd {
    public static void main(String[] args) {
        try {
            // 创建 HttpClient 实例
            HttpClient httpClient = HttpClientBuilder.create().build();

            // 构建请求 URL
            URI url = new URIBuilder("https://sdp246.hadoop.com/ipa/session/json").build();

            // 创建 HTTP POST 请求
            HttpPost httpPost = new HttpPost(url);

            // 设置请求头
            httpPost.setHeader(HttpHeaders.ACCEPT, "application/json, text/javascript, */*; q=0.01");
            httpPost.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
            httpPost.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            httpPost.setHeader(HttpHeaders.CONNECTION, "keep-alive");
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader("Cookie", "ipa_session=MagBearerToken=A1Riivw34KneOudFu%2bmZlpe8pC2vxXKpxmEGHq3ZGwsLoXZgX5WuxrPfY9pMn1VPAJlmybvPTVRCjjueYhA%2fwDPmiE7qsg4YYdYuBF89zYr1uXgrz5acrYDdNQyP1NtlU0DnHBi7SNTf8Ug7F6%2fKHjPdzlbYl0iKrn6xiNxICZ%2b%2bZcGmuXNpXO0irTZxyI5Ae3kpDbVAGZTvA8K8yTmpTg%3d%3d; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
            httpPost.setHeader("Origin", "https://sdp246.hadoop.com");
            httpPost.setHeader(HttpHeaders.PRAGMA, "no-cache");
            httpPost.setHeader(HttpHeaders.REFERER, "https://sdp246.hadoop.com/ipa/ui/");
            httpPost.setHeader("Sec-Fetch-Dest", "empty");
            httpPost.setHeader("Sec-Fetch-Mode", "cors");
            httpPost.setHeader("Sec-Fetch-Site", "same-origin");
            httpPost.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.setHeader("sec-ch-ua", "\"Google Chrome\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"");
            httpPost.setHeader("sec-ch-ua-mobile", "?0");
            httpPost.setHeader("sec-ch-ua-platform", "\"Windows\"");

            // 设置请求体
            String requestBody = "{\"method\":\"passwd\",\"params\":[[\"jiao8hong8\"],{\"current_password\":\"mima123456\",\"password\":\"123456\",\"version\":\"2.237\"}]}";
            httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpPost);

            // 读取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            StringBuilder responseBody = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();

            // 输出响应结果
            System.out.println(responseBody.toString());

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
