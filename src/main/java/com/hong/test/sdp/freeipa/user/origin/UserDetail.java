package com.hong.test.sdp.freeipa.user.origin;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Result;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserDetail {
    public static void main(String[] args) {
        System.out.println(show("hjiao"));
    }

    public static Result show(String username) {
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
            httpPost.setHeader("Cookie", "ipa_session=MagBearerToken=GO6CZlKvMmbJoQ46dQIdB9VLLinIVMctAlzWqHHQFn%2bkATULqmV7GP4yYmywe%2fZQ9pG4mh77RzlGmIl3Kt9tdW5VBMSyksaGS2of4UPSkylYOWmXhv13RblcupmC1kPBheKhMUDwYlF7DDgwBZMW4kE0vlvMateqxFhBcsGfFN21QU%2bQSqn7CWJyDZfqHggY; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
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
            String requestBody = "{\"method\":\"user_show/1\",\"params\":[[\"" + username + "\"]," + "{\"all\":true,\"version\":\"2.237\"}]}";

            List<Object> params = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("all", true);
            map.put("version", "2.237");
            params.add(map);
            params.add(new String[]{username});

            IpaParam.builder().method("user_show/1").params(params);

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
            String result = responseBody.toString();
            JSONObject jsonResult = JSONObject.parseObject(result);
            System.out.println(jsonResult);
            JSONObject errorResult = jsonResult.getJSONObject("error");
            if (errorResult != null && errorResult.size() > 0) {
                String errorMsg = errorResult.getJSONObject("data").getString("reason");
                return Result.failed(errorMsg);
            }

            JSONObject userResult = jsonResult.getJSONObject("result").getJSONObject("result");
            return Result.success(userResult);
        } catch (URISyntaxException | IOException e) {
            log.error("请求异常：", e);
        }

        return Result.failed("请求失败，查询日志");
    }

}

// 正确信息
//{
//    "result":{
//        "result":{
//            "mail":[
//                "hjiao@hadoop.com"
//            ],
//            "ipauniqueid":[
//                "516962e2-5204-11ee-8926-52540052bdef"
//            ],
//            "dn":"uid=hjiao,cn=users,cn=accounts,dc=hadoop,dc=com",
//            "krblastpwdchange":[
//                {
//                    "__datetime__":"20230913071529Z"
//                }
//            ],
//            "objectclass":[
//                "top",
//                "person",
//                "organizationalperson",
//                "inetorgperson",
//                "inetuser",
//                "posixaccount",
//                "krbprincipalaux",
//                "krbticketpolicyaux",
//                "ipaobject",
//                "ipasshuser",
//                "ipaSshGroupOfPubKeys",
//                "mepOriginEntry"
//            ],
//            "loginshell":[
//                "/bin/sh"
//            ],
//            "krbloginfailedcount":[
//                "0"
//            ],
//            "uid":[
//                "hjiao"
//            ],
//            "homedirectory":[
//                "/home/hjiao"
//            ],
//            "krbpasswordexpiration":[
//                {
//                    "__datetime__":"20231212071529Z"
//                }
//            ],
//            "preserved":false,
//            "mepmanagedentry":[
//                "cn=hjiao,cn=groups,cn=accounts,dc=hadoop,dc=com"
//            ],
//            "givenname":[
//                "hong"
//            ],
//            "krblastfailedauth":[
//                {
//                    "__datetime__":"20230913071743Z"
//                }
//            ],
//            "krbpwdpolicyreference":[
//                "cn=ipausers,cn=HADOOP.COM,cn=kerberos,dc=hadoop,dc=com"
//            ],
//            "sn":[
//                "jiao"
//            ],
//            "krbextradata":[
//                {
//                    "__base64__":"AAKRYQFlaGppYW9ASEFET09QLkNPTQA="
//                }
//            ],
//            "has_password":true,
//            "has_keytab":true,
//            "initials":[
//                "hj"
//            ],
//            "krbcanonicalname":[
//                "hjiao@HADOOP.COM"
//            ],
//            "gidnumber":[
//                "1683800012"
//            ],
//            "krbprincipalname":[
//                "hjiao@HADOOP.COM"
//            ],
//            "cn":[
//                "hong jiao"
//            ],
//            "memberof_group":[
//                "ipausers"
//            ],
//            "uidnumber":[
//                "1683800012"
//            ],
//            "gecos":[
//                "hong jiao"
//            ],
//            "nsaccountlock":false,
//            "displayname":[
//                "hong jiao"
//            ]
//        },
//        "value":"hjiao"
//    },
//    "principal":"admin@HADOOP.COM",
//    "version":"4.6.8"
//}

// 错误信息
//{
//    "result":null,
//    "version":"4.6.8",
//    "error":{
//        "message":"hhhh\uff1a\u7528\u6237\u6ca1\u6709\u627e\u5230",
//        "code":4001,
//        "data":{
//            "reason":"hhhh\uff1a\u7528\u6237\u6ca1\u6709\u627e\u5230"
//        },
//        "name":"NotFound"
//    },
//    "id":null,
//    "principal":"admin@HADOOP.COM"
//}