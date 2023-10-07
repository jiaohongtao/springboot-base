package com.hong.test.sdp.freeipa.user.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtil {

    private static final String FREEIPA_BASE_PROTOCOL = "https";
    private static final String FREEIPA_BASE_URL = "sdp246.hadoop.com";

    private static final String ACCEPT = "application/json, text/javascript, */*; q=0.01";
    private static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    private static final String CACHE_CONTROL = "no-cache";
    private static final String CONNECTION = "keep-alive";
    private static final String CONTENT_TYPE = "application/json";
    private static final String ORIGIN = FREEIPA_BASE_PROTOCOL + "://" + FREEIPA_BASE_URL;
    private static final String PRAGMA = "no-cache";
    private static final String REFERER = FREEIPA_BASE_PROTOCOL + "://" + FREEIPA_BASE_URL + "/ipa/ui/";
    private static final String SEC_FETCH_DEST = "empty";
    private static final String SEC_FETCH_MODE = "cors";
    private static final String SEC_FETCH_SITE = "same-origin";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36";
    private static final String X_REQUESTED_WITH = "XMLHttpRequest";
    private static final String SEC_CH_UA = "\"Google Chrome\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"";
    private static final String SEC_CH_UA_MOBILE = "?0";
    private static final String SEC_CH_UA_PLATFORM = "\"Windows\"";
    public static final String CONTENT_TYPE_FORM_DATA = "application/x-www-form-urlencoded";

    /*public static HttpPost createFreeipaJsonClient(String method, String requestBody, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException {
        return createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
    }*/

    public static HttpPost createHttpPost(String url, String requestBody) throws URISyntaxException, UnsupportedEncodingException {
        return createHttpPost(url, requestBody, new HashMap<>());
    }

    public static HttpPost createHttpPost(String url, String requestBody, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException {
        log.info("请求地址为: \n【{}】\n参数为: \n【{}】", url, requestBody);

        URI uri = new URIBuilder(url).build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(HttpHeaders.ACCEPT, ACCEPT);
        httpPost.setHeader(HttpHeaders.ACCEPT_LANGUAGE, ACCEPT_LANGUAGE);
        httpPost.setHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL);
        httpPost.setHeader(HttpHeaders.CONNECTION, CONNECTION);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
        // httpPost.setHeader(HttpHeaders.COOKIE, COOKIE);
        httpPost.setHeader(HttpHeaders.ORIGIN, ORIGIN);
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

        headers.forEach(httpPost::setHeader);

        return httpPost;
    }

    /**
     * 执行并获取响应体
     *
     * @param httpPost httpPost
     * @return 响应体
     * @throws IOException IOException
     */
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

    /**
     * 执行获取cookie
     *
     * @param httpPost httpPost
     * @return cookie
     * @throws IOException IOException
     */
    public static String loginGetCookie(HttpPost httpPost) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpPost);

        // 获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            // 提取响应头的 cookie 信息
            return response.getFirstHeader("Set-Cookie").getValue();
        }
        return "";
    }
}
