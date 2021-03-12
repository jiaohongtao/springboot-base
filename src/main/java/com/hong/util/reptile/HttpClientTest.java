package com.hong.util.reptile;

import cn.hutool.crypto.SecureUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author jiaohongtao
 */
public class HttpClientTest {

    public static void main(String[] args) {
        getCnBlogsHtml();
    }

    /**
     * 获取博客园文章信息
     */
    public static void getCnBlogsHtml() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入网址
        String url = "https://www.cnblogs.com/";
        // url = "https://www.tuicool.com/";
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");

        // 设置代理IP，防止禁用IP
        /*HttpHost proxy = new HttpHost("39.105.206.57", 8089);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        request.setConfig(config);*/

        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(html);

                // 最终解析html
                Document document = Jsoup.parse(html);
                //像js一样，通过标签获取title
                System.out.println(document.getElementsByTag("title").first());
                //像js一样，通过id 获取文章列表元素对象
                Element postList = document.getElementById("post_list");
                //像js一样，通过class 获取列表下的所有博客
                Elements postItems = postList.getElementsByClass("post-item");
                //循环处理每篇博客
                for (Element postItem : postItems) {
                    //像jquery选择器一样，获取文章标题元素
                    Elements titleEle = postItem.select(".post-item-body .post-item-text a[class='post-item-title']");
                    System.out.println("文章标题:" + titleEle.text());
                    System.out.println("文章地址:" + titleEle.attr("href"));
                    //像jquery选择器一样，获取文章作者元素
                    /*Element element = postItem.selectFirst(".post-item-foot a[class='post-item-author'] span");
                    System.out.println("文章作者：" + element.text());*/

                    Elements footEle = postItem.select(".post-item-foot a[class='post-item-author']");
                    System.out.println("文章作者:" + footEle.text());
                    System.out.println("作者主页:" + footEle.attr("href"));

                    Elements dateEle = postItem.select(".post-item-foot span[class='post-meta-item'] span");
                    System.out.println("更新时间:" + dateEle.text());
                    Elements countEle = postItem.select(".post-item-foot a[class='post-meta-item btn'] span");
                    System.out.println("点赞数:" + countEle.get(0).text());
                    System.out.println("评论数:" + countEle.get(1).text());
                    System.out.println("阅读数:" + countEle.get(2).text());
                    System.out.println("*********************************");
                }
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }
}