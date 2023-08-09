package com.hong.study.io.bio;

import java.io.*;
import java.net.Socket;

public class BioClientTest {

    public static void main(String[] args) {
        //创建10个线程，模拟10个客户端，同时向服务端发送请求
        for (int i = 0; i < 50; i++) {
            final int j = i;//定义变量
            new Thread(() -> {
                try {
                    //通过IP和端口与服务端建立连接
                    Socket socket =new Socket("127.0.0.1",8080);
                    //将字符流转化成字节流，并输出
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                    String str="Hello，我是" + j + "个，客户端!";
                    printWriter.println(str);

                    //从输入流中读取服务端返回的信息，将字节流转化成字符流
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //读取内容
                    String result = bufferedReader.readLine();
                    //打印服务端返回的信息
                    System.out.println("客户端发送请求内容：" + str + " -> 收到服务端返回的内容：" + result);

                    // 关闭流
                    bufferedReader.close();
                    printWriter.close();
                    // 关闭socket
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}