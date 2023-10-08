package com.hong.study.io.nio.simple;

import java.io.*;

public class StringRead {

    public static void main(String[] args) {
        long st = System.currentTimeMillis();

        String fname = "KMSTools_active.zip";
        long fileLength = 0;// 文件总长
        final long limitLength = 1024 * 1024 * 50; // 分割文件大小
        long outLength = 0;// 输出文件长度

        BufferedInputStream bis = null;
        BufferedReader in = null;
        FileWriter fw = null;
        // String filePath_r = "F:\\fileTest\\" + fname + ".log";
        String filePath_r = "D:\\Mixed\\" + fname + ".log";
        try {
            File file_r = new File(filePath_r);
            fileLength = file_r.length();
            bis = new BufferedInputStream(new FileInputStream(file_r));
            in = new BufferedReader(new InputStreamReader(bis));

            int count = 0;
            for (int i = 0; i < fileLength / limitLength + 1; i++) {
                fw = new FileWriter("D:\\Mixed\\" + fname + "_" + count + ".log");

                String line;
                long j = 0;
                while (j < limitLength && outLength < fileLength) {
                    line = in.readLine();
                    if (null != line) {
                        fw.append(line).append("\r");
                        j = j + line.length();// 统计单个输出文件大小，当其超过imitLength时中止循环
                        outLength = outLength + line.length();// 统计总输出文件大小，当其超过fileLength时中止循环
                    } else {
                        break;
                    }
                }

                fw.flush();
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
                in.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long ed = System.currentTimeMillis();

        System.err.println(filePath_r + "大小" + fileLength / (1024 * 1024) + "M处理用时：" + (ed - st) / 1000 + "s");
    }
}