package com.hong.util.httpRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 使用httpclient调用外部接口
 * href: https://blog.csdn.net/qq_43442817/article/details/100659801
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年05月13日
 */
public class HttpClientUtil {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36";

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
            httpGet.setHeader("User-Agent", USER_AGENT);

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
            httpPost.setHeader("User-Agent", USER_AGENT);
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

    /**
     * 发送post raw数据
     *
     * @param url    url
     * @param querys url参数
     * @param param  raw 参数 {"a":"b"}
     */
    public static String httpPostRaw(String url, List<JSONObject> querys, JSONObject param) throws IOException {
        // 拼接url及参数
        StringBuilder urlBuilder = new StringBuilder(url);
        if (null != querys && !querys.isEmpty()) {
            urlBuilder.append("?");
            for (JSONObject query : querys) {
                for (Map.Entry<String, Object> entry : query.entrySet()) {
                    urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
            url = urlBuilder.toString();
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        // json传递
        post.setEntity(new StringEntity(param.toJSONString()));
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        return EntityUtils.toString(response.getEntity());
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

        // String resultGet = HttpClientUtil.doHttpGet(url, list);
        // System.out.println("get请求：" + resultGet);
        // String resultPost = HttpClientUtil.doPost(url, list);
        // System.out.println("post请求：" + resultPost);


        // String u = "https://api.apiopen.top/getAllUrl";
        // String u = "https://api.apiopen.top/getWangYiNews";
        String u = "http://meiriyikan.cn/api/json.php";
        List<NameValuePair> apiOpens = new ArrayList<>();
        // apiOpens.add(new BasicNameValuePair("page", "1"));
        // apiOpens.add(new BasicNameValuePair("count", "10"));
        System.out.println(HttpClientUtil.doHttpGet(u, apiOpens));
    }
}
