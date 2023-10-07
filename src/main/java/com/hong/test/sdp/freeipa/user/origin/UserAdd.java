package com.hong.test.sdp.freeipa.user.origin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserAdd {

    public static String addUser(String url, String payload, String cookie) {
        StringBuilder response = new StringBuilder();

        try {
            URL endpoint = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Cookie", cookie);
            connection.setRequestProperty("Origin", "https://sdp246.hadoop.com");
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Referer", "https://sdp246.hadoop.com/ipa/ui/");
            connection.setRequestProperty("Sec-Fetch-Dest", "empty");
            connection.setRequestProperty("Sec-Fetch-Mode", "cors");
            connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"116\", \"Not)A;Brand\";v=\"24\", \"Google Chrome\";v=\"116\"");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            connection.setDoOutput(true);

            connection.getOutputStream().write(payload.getBytes());

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
            } else {
                System.out.println("Request failed with HTTP error code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static void main(String[] args) {
        String url = "https://sdp246.hadoop.com/ipa/session/json";
        String payload = "{\"method\":\"user_add\",\"params\":[[],{\"givenname\":\"hong4\",\"sn\":\"jiao4\", \"userpassword\": \"12345678\",\"version\":\"2.237\"}]}";
        String cookie = "ipa_session=MagBearerToken=%2f07hG%2fp8jlfum%2bbkj7WlabqwU3qdKl9kAW%2br7LtBRk%2fHN1V5GJed4djiY7neMCITG2KmBLYh7Vmns7a2Rk1CDTdPMlLAyxS9CDveYLfKV6GqvT3zwrkZxYPV1XeYrZ18f0OJhiDpqhFlmXxwlJ9gX%2bq%2fneJR7pVh0aRwdpCOLxcpnMaA%2fecqEaYy0RrKBjEi; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";

        String response = addUser(url, payload, cookie);
        System.out.println("Response: " + response);

        // 1.创建用户，可随意指定密码（后面会修改为正确密码）
        // 2.设置真正密码（模拟登录并重置密码操作）
    }
}
