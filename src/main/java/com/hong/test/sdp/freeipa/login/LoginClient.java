package com.hong.test.sdp.freeipa.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/13
 */
public class LoginClient {

    // public static String sendPostRequest(String url, String payload, String cookie) {
    public static String sendPostRequest(String url, String payload) {
        StringBuilder response = new StringBuilder();

        try {
            URL endpoint = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "text/html, */*; q=0.01");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // connection.setRequestProperty("Cookie", cookie);
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

                // 提取 Cookie 信息
                String cookieHeader = connection.getHeaderField("Set-Cookie");
                if (cookieHeader != null) {
                    String[] cookies = cookieHeader.split(";");
                    for (String cookie : cookies) {
                        if (cookie.contains("=")) {
                            String cookieName = cookie.substring(0, cookie.indexOf("="));
                            String cookieValue = cookie.substring(cookie.indexOf("=") + 1);
                            /*String[] parts = cookie.split("=");
                            String cookieName = parts[0].trim();
                            String cookieValue = parts[1].trim();*/
                            // 使用 cookieName 和 cookieValue 进行进一步处理
                            System.out.println("Cookie Name: " + cookieName);
                            System.out.println("Cookie Value: " + cookieValue);
                        }
                    }
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

    public static String getCookie(String url, String payload) {
        try {
            URL endpoint = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "text/html, */*; q=0.01");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // connection.setRequestProperty("Cookie", cookie);
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

                // 提取 Cookie 信息
                String cookieHeader = connection.getHeaderField("Set-Cookie");
                if (cookieHeader != null) {
                    String[] cookies = cookieHeader.split(";");
                    for (String cookie : cookies) {
                        if (cookie.contains("ipa_session") && cookie.contains("=")) {
                            return cookie;
                        }
                    }
                }

                reader.close();
            } else {
                System.out.println("Request failed with HTTP error code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        String url = "https://sdp246.hadoop.com/ipa/session/login_password";
        // String requestPayload = "user=admin&password=mima123456";
        String requestPayload = "user=hjiao&password=12345678";
//        String cookie = "grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";

        // String response = sendPostRequest(url, requestPayload, cookie);
        String response = sendPostRequest(url, requestPayload);
        System.out.println("Response: " + response);
    }
}
