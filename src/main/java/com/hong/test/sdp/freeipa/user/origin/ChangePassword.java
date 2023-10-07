package com.hong.test.sdp.freeipa.user.origin;

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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class ChangePassword {

    private static final String ACCEPT = "text/html, */*; q=0.01";
    private static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    private static final String CACHE_CONTROL = "no-cache";
    private static final String CONNECTION = "keep-alive";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String ORIGIN = "https://sdp246.hadoop.com";
    private static final String PRAGMA = "no-cache";
    private static final String REFERER = "https://sdp246.hadoop.com/ipa/ui/";
    private static final String SEC_FETCH_DEST = "empty";
    private static final String SEC_FETCH_MODE = "cors";
    private static final String SEC_FETCH_SITE = "same-origin";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36";
    private static final String X_REQUESTED_WITH = "XMLHttpRequest";
    private static final String SEC_CH_UA = "\"Google Chrome\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"";
    private static final String SEC_CH_UA_MOBILE = "?0";
    private static final String SEC_CH_UA_PLATFORM = "\"Windows\"";
    private static final String COOKIE = "grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";

    public static void main(String[] args) {
        try {
            String url = "https://sdp246.hadoop.com/ipa/session/change_password";
            String requestBody = "user=apiadd002&old_password=12345678&new_password=123456";

            HttpPost httpPost = createHttpPost(url, requestBody);
            String responseBody = executeHttpPost(httpPost);

            System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpPost createHttpPost(String url, String requestBody) throws URISyntaxException, UnsupportedEncodingException {
        URI uri = new URIBuilder(url).build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(HttpHeaders.ACCEPT, ACCEPT);
        httpPost.setHeader(HttpHeaders.ACCEPT_LANGUAGE, ACCEPT_LANGUAGE);
        httpPost.setHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL);
        httpPost.setHeader(HttpHeaders.CONNECTION, CONNECTION);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
        httpPost.setHeader("Cookie", COOKIE);
        httpPost.setHeader("Origin", ORIGIN);
        httpPost.setHeader(HttpHeaders.PRAGMA, PRAGMA);
        httpPost.setHeader(HttpHeaders.REFERER, REFERER);
        httpPost.setHeader("Sec-Fetch-Dest", SEC_FETCH_DEST);
        httpPost.setHeader("Sec-Fetch-Mode", SEC_FETCH_MODE);
        httpPost.setHeader("Sec-Fetch-Site", SEC_FETCH_SITE);
        httpPost.setHeader(HttpHeaders.USER_AGENT, USER_AGENT);
        httpPost.setHeader("X-Requested-With", X_REQUESTED_WITH);
        httpPost.setHeader("sec-ch-ua", SEC_CH_UA);
        httpPost.setHeader("sec-ch-ua-mobile", SEC_CH_UA_MOBILE);
        httpPost.setHeader("sec-ch-ua-platform", SEC_CH_UA_PLATFORM);
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
        return httpPost;
    }

    public static String executeHttpPost(HttpPost httpPost) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder responseBody = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }
        reader.close();
        return responseBody.toString();
    }
}
