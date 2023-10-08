package com.hong.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 百度推送
 * @author jiaohongtao
 */
public class BaiduTuiSong {

    private String post(String postUrl, String[] parameters) {
        if (null == postUrl || null == parameters || parameters.length == 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(postUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", "83");
            conn.setRequestProperty("Content-Type", "text/plain");

            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            StringBuilder param = new StringBuilder();
            for (String s : parameters) {
                param.append(s).append("\n");
            }
            out.print(param.toString().trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            System.out.println("发送post请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private void testBaidu() {
        //网站的服务器连接
        String url = "http://data.zz.baidu.com/urls?site=blog.jiaohongtao.com&token=pb4UE61ETBjVhKyp";
        String[] param = {
                "https://blog.jiaohongtao.com/2019/12/25/html%E8%8E%B7%E5%8F%96%E5%BD%93%E5%89%8D%E9%A1%B9%E7%9B%AE%E6%A0%B9%E8%B7%AF%E5%BE%84/index.html"
        };
        String json = post(url, param);//执行推送方法
        System.out.println("结果是" + json);  //打印推送结果
    }

    public static void main(String[] args) {
        BaiduTuiSong baiduTuiSong = new BaiduTuiSong();
        baiduTuiSong.testBaidu();
    }

}
