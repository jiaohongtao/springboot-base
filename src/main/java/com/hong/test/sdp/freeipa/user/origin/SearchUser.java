package com.hong.test.sdp.freeipa.user.origin;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUser {

    public static String searchUser(String url, String payload) {
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
            connection.setRequestProperty("Cookie", "ipa_session=MagBearerToken=HpuyZ84iHERXFqzTUy3%2fgcaNg7pNEmDAhnNx0DYZg0Bo4qNqIuLQ00slcHVXvMfao%2fwB2FBiASf%2fkFva9BUGg4LdTIWh1etvfuON43iypKAJGZMSHkZ5sFmAhp3qU0cjNi1MTjHp3XrpM6D8cC%2fU58nr%2btUcH961Dp%2bDPKbUcK6HHBPccQRpHZuPVDMZxf3K; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
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

    public static String list(String username) {
        String url = "https://sdp246.hadoop.com/ipa/session/json";
        // String requestPayload = "{\"method\":\"user_find\",\"params\":[[\"\"],{\"pkey_only\":true,\"sizelimit\":0,\"version\":\"2.237\"}]}";

        IpaParam.IpaParamBuilder ipaParamBuilder = IpaParam.builder().method("user_find");

        List<Object> params = new ArrayList<>();
        Map<String, Object> baseCondition = new HashMap<>();
        baseCondition.put("pkey_only", true);
        baseCondition.put("sizelimit", 0);
        baseCondition.put("version", "2.237");
        params.add(baseCondition);
        params.add(StringUtils.isNotBlank(username) ? new String[]{username} : new String[]{});
        IpaParam ipaParam = ipaParamBuilder.params(params).build();

        String response = searchUser(url, JSONObject.toJSONString(ipaParam));
        System.out.println("Response: " + response);
        return response;
    }

    public static void main(String[] args) {
        System.out.println(list(""));
    }

    /**
     * 从查询结果中获取用户
     *
     * @param username   校验用户名
     * @param findResult 查询结果
     * @return 是否存在
     */
    public boolean getUser(String username, String findResult) {
        JSONObject jsonObject = JSONObject.parseObject(findResult);
        JSONObject result = jsonObject.getJSONObject("result");
        List<JSONObject> users = result.getJSONArray("result").toJavaList(JSONObject.class);
        for (JSONObject user : users) {
            if (username.equals(user.getJSONArray("uid").get(0))) {
                return true;
            }
        }
        return false;
    }
}
