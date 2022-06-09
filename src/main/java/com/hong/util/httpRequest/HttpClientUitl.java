package com.hong.util.httpRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用httpclient调用外部接口
 * href: https://blog.csdn.net/qq_43442817/article/details/100659801
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年05月13日
 */
public class HttpClientUitl {
    /**
     * get 请求
     */
    public static String doHttpGet(String url, List<NameValuePair> params) {
        String result = null;
        //1.获取httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //接口返回结果
        CloseableHttpResponse response = null;
        String paramStr;
        try {
            //拼接参数
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (params != null && !params.isEmpty()) {
                paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                sb.append("?").append(paramStr);
            }

            //2.创建get请求
            HttpGet httpGet = new HttpGet(sb.toString());
            //3.设置请求和传输超时时间
            // RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpGet.setConfig(requestConfig);
            /*此处可以添加一些请求头信息，例如：
            httpGet.addHeader("content-type","text/xml");*/
            //4.提交参数
            response = httpClient.execute(httpGet);
            //5.得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            //6.判断响应信息是否正确
            if (HttpStatus.SC_OK != statusCode) {
                //终止并抛出异常
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            //7.转换成实体类
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //8.关闭所有资源连接
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * http post 请求
     */
    public static String doPost(String url, List<NameValuePair> params) {
        String result = null;
        //1. 获取httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            //2. 创建post请求
            HttpPost httpPost = new HttpPost(url);

            //3.设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpPost.setConfig(requestConfig);

            //4.提交参数发送请求
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params);
            /*此处可以设置传输时的编码格式，和数据格式
            urlEncodedFormEntity.setContentEncoding("UTF-8");
            urlEncodedFormEntity.setContentType("application/json");*/
            httpPost.setEntity(urlEncodedFormEntity);

            response = httpClient.execute(httpPost);

            //5.得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            //6. 判断响应信息是否正确
            if (HttpStatus.SC_OK != statusCode) {
                //结束请求并抛出异常
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            //7. 转换成实体类
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //8. 关闭所有资源连接
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // String url = "https://restapi.amap.com/v3/ip"; // ip
        // String url = "https://restapi.amap.com/v3/weather/weatherInfo"; // weather
        String url = "https://restapi.amap.com/v3/assistant/coordinate/convert"; // location

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("key", "43b2dae85b7a78ed9ff10f40427c1d8b"));
        // list.add(new BasicNameValuePair("ip", "39.105.206.57"));

        // list.add(new BasicNameValuePair("city", "130527"));
        // base:返回实况天气 all:返回预报天气
        // list.add(new BasicNameValuePair("extensions", "base"));

        // 113.9629412,22.4627142;114.2106056,22.61394155
        list.add(new BasicNameValuePair("locations", "113.9629412,22.4627142;114.2106056,22.61394155"));

        // String resultGet = HttpClientUitl.doHttpGet(url, list);
        // System.out.println("get请求：" + resultGet);
        // String resultPost = HttpClientUitl.doPost(url, list);
        // System.out.println("post请求：" + resultPost);


        // String u = "https://api.apiopen.top/getAllUrl";
        // String u = "https://api.apiopen.top/getWangYiNews";
        String u = "http://meiriyikan.cn/api/json.php";
        List<NameValuePair> apiOpens = new ArrayList<>();
        // apiOpens.add(new BasicNameValuePair("page", "1"));
        // apiOpens.add(new BasicNameValuePair("count", "10"));
        System.out.println(HttpClientUitl.doHttpGet(u, apiOpens));
    }
}
