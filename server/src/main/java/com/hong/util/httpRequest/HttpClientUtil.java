package com.hong.util.httpRequest;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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
@Slf4j
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
                paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
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

    public static String httpGet(String url, List<JSONObject> querys, JSONObject param) {
        return httpGet(url, querys, param, new HashMap<>());
    }

    public static String httpGetAndHeader(String url, List<JSONObject> querys, Map<String, String> header) {
        return httpGet(url, querys, null, header);
    }

    public static String httpGet(String url, List<JSONObject> querys, JSONObject param, Map<String, String> header) {
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
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-type", "application/json");
        header.forEach(get::addHeader);

        HttpResponse response;
        try {
            response = httpClient.execute(get);
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
        try {
            assert response != null;
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
    }

    public static String sendPost(String url, JSONObject params, File file, Map<String, String> header) {
        HttpClient httpClient = new DefaultHttpClient();
        String result = null;
        HttpPost post = new HttpPost(url);// 创建HttpPost对象
        header.forEach(post::addHeader);
        // 如果传递参数个数比较多的话可以对传递的参数进行封装
        MultipartEntity entity = new MultipartEntity();
        try {
            for (String key : params.keySet()) {    // 封装请求参数
                //避免传递汉字出现乱码
                StringBody value = new StringBody(params.getString(key), StandardCharsets.UTF_8);
                entity.addPart(new FormBodyPart(key, value));
            }
            if (file != null) {
                entity.addPart("file", new FileBody(file));
            }
            post.setEntity(entity);// 设置请求参数
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送post raw数据
     * https://blog.csdn.net/qq_27605885/article/details/80460463
     * https://www.cnblogs.com/ifindu-san/p/9277967.html
     *
     * @param url    url
     * @param querys url参数
     * @param param  raw 参数 {"a":"b"}
     */
    public static String httpPostRaw(String url, List<JSONObject> querys, JSONObject param) {
        return httpPostRaw(url, querys, param, new HashMap<>());
    }

    public static String httpPostRaw(String url, List<JSONObject> querys, JSONObject param, Map<String, String> header) {
        return httpPostData(url, querys, param, header);
    }

    public static <T> String httpPostData(String url, List<JSONObject> querys, T param) {
        return httpPostData(url, querys, param, new HashMap<>());
    }

    public static <T> String httpPostData(String url, List<JSONObject> querys, T param, Map<String, String> header) {
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
        header.forEach(post::addHeader);

        // json传递
        post.setEntity(new StringEntity(JSONObject.toJSONString(param), StandardCharsets.UTF_8));
        post.setHeader("Content-type", "application/json");
        HttpResponse response;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
        try {
            assert response != null;
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
    }

    public static String httpDeleteRaw(String url, List<JSONObject> querys, JSONObject param, Map<String, String> header) {
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
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-type", "application/json");
        header.forEach(httpDelete::addHeader);

        /*// json传递
        try {
            httpDelete.setEntity(new StringEntity(param.toJSONString()));
        } catch (UnsupportedEncodingException e) {
            log.error("字符编码不支持：", e);
            return null;
        }*/
        HttpResponse response;
        try {
            response = httpClient.execute(httpDelete);
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
        try {
            assert response != null;
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
    }

    /**
     * 发送post raw数据
     * https://blog.csdn.net/qq_27605885/article/details/80460463
     * https://www.cnblogs.com/ifindu-san/p/9277967.html
     *
     * @param url    url
     * @param querys url参数
     * @param param  raw 参数 {"a":"b"}
     */
    public static String httpPutRaw(String url, List<JSONObject> querys, JSONObject param) {
        return httpPutRaw(url, querys, param, new HashMap<>());
    }

    public static String httpPutRaw(String url, List<JSONObject> querys, JSONObject param, Map<String, String> header) {
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
        HttpPut put = new HttpPut(url);
        header.forEach(put::addHeader);

        // json传递
        try {
            put.setEntity(new StringEntity(param.toJSONString()));
        } catch (UnsupportedEncodingException e) {
            log.error("字符编码不支持：", e);
            return null;
        }
        put.setHeader("Content-type", "application/json");
        HttpResponse response;
        try {
            response = httpClient.execute(put);
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
        try {
            assert response != null;
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("IO读取异常：", e);
            return null;
        }
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
