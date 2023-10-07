package com.hong.test.sdp.freeipa.user.origin;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UserDel {
    public static void main(String[] args) {
        System.out.println(delUser("jiaoldap"));
    }

    public static String delUser(String username) {
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

            String cookie = "ipa_session=MagBearerToken=kAcQgX6Z5l%2fvJysMuU6bICGEyv7nO8j8JqLHJieuICOsGuiP2XuGSH2ECuT5vNnl3Ix%2b2Nnz%2fQCNRrKHaUNS8A3qepSFlgqSge0jFw%2bKf87pSM%2bf6nQFS9LCELLMGctYxJr62ezEH7CRLGq7mlUQ82c930sPKvqAT3N4TVrE25486XP5lkbiE56LydDBme9s; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";
            //httpPost.setHeader(HttpHeaders.COOKIE, cookie);
            //httpPost.setHeader(HttpHeaders.ORIGIN, "https://sdp246.hadoop.com");
            httpPost.setHeader("Cookie", cookie);
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
            String requestBody = "{\"method\":\"user_del\",\"params\":[[\"" + username + "\"]," +
                    "{\"preserve\":\"false\",\"version\":\"2.237\"}]}";
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
            System.out.println(responseBody);

            log.info("删除成功");
            return responseBody.toString();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        log.error("删除失败");
        return null;
    }
}
