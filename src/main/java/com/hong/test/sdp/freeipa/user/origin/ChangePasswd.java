package com.hong.test.sdp.freeipa.user.origin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/13
 */
public class ChangePasswd {
    public static String changePasswd(String url, String payload, String cookie) {
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "https://sdp246.hadoop.com/ipa/session/change_password";
        String user = "hjiao2";
        String oldPassword = "12345678";
        String newPassword = "mima123456";
        String payload = "user=" + URLEncoder.encode(user, "UTF-8") +
                "&old_password=" + URLEncoder.encode(oldPassword, "UTF-8") +
                "&new_password=" + URLEncoder.encode(newPassword, "UTF-8");
        String cookie = "grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";

        String response = changePasswd(url, payload, cookie);
        System.out.println("Response: " + response);
    }
}
