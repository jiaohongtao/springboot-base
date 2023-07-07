package com.hong.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 文件下载工具类
 * https://blog.csdn.net/masuwen/article/details/53203027
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/07
 */
public class DownloadUtil {

    public static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    public static void main(String[] args) {
        // 下载列表
        Vector<String> downloadList = new Vector<>();
        // 添加下载地址
        downloadList.add("https://foruda.gitee.com/avatar/1677041339136332301/5216809_fresh_gitee_1610008837.png");
        downloadList.add("https://foruda.gitee.com/images/1688439495785399480/b1b4846a_6556755.png");
        downloadUrlFiles(downloadList);
    }

    /**
     * 下载多个地址文件到本地
     *
     * @param downloadList 地址集合
     */
    static void downloadUrlFiles(Vector<String> downloadList) {
        // 线程池
        ExecutorService pool = null;
        HttpURLConnection connection = null;
        //循环下载
        try {
            for (int i = 0; i < downloadList.size(); i++) {
                pool = Executors.newCachedThreadPool();
                final String url = downloadList.get(i);
                String filename = getFilename(downloadList.get(i));
                System.out.println("正在下载第" + (i + 1) + "个文件，地址：" + url);
                Future<HttpURLConnection> future = pool.submit(() -> {
                    HttpURLConnection connection1 = (HttpURLConnection) new URL(url).openConnection();
                    connection1.setConnectTimeout(10000);//连接超时时间
                    connection1.setReadTimeout(10000);// 读取超时时间
                    connection1.setDoInput(true);
                    connection1.setDoOutput(true);
                    connection1.setRequestMethod("GET");
                    //断点续连,每次要算出range的范围,请参考Http 1.1协议
                    //connection.setRequestProperty("Range", "bytes=0");
                    connection1.connect();
                    return connection1;
                });
                connection = future.get();
                System.out.println("下载完成.响应码:" + connection.getResponseCode());
                // 写入文件
                writeFile(new BufferedInputStream(connection.getInputStream()), URLDecoder.decode(filename, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
            if (null != pool) {
                pool.shutdown();
            }
        }
    }

    /**
     * 通过截取URL地址获得文件名
     * 注意：还有一种下载地址是没有文件后缀的，这个需要通过响应头中的
     * Content-Disposition字段 获得filename，一般格式为："attachment; filename=\xxx.exe\"
     *
     * @param url 地址
     * @return 文件名
     */
    static String getFilename(String url) {
        return ("".equals(url) || null == url) ? "" : url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 写入文件
     *
     * @param bufferedInputStream 缓存输入流
     */
    static void writeFile(BufferedInputStream bufferedInputStream, String filename) {
        //创建本地文件
        File destFile = new File("d:\\" + filename);
        if (destFile.exists()) {
            boolean delete = destFile.delete();
            logger.info("删除：{}", delete);
        }
        if (!destFile.getParentFile().exists()) {
            boolean mkdir = destFile.getParentFile().mkdir();
            logger.info("创建：{}", mkdir);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(destFile);
            byte[] b = new byte[1024];
            int len;
            // 写入文件
            System.out.println("开始写入本地文件.");
            while ((len = bufferedInputStream.read(b, 0, b.length)) != -1) {
                System.out.println("正在写入字节：" + len);
                fileOutputStream.write(b, 0, len);
            }
            System.out.println("写入本地文件完成.");
        } catch (FileNotFoundException e) {
            logger.error("文件不存在");
        } catch (IOException e) {
            logger.error("读写异常");
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                if (null != bufferedInputStream) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
