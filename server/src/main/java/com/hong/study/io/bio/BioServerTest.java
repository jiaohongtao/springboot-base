package com.hong.study.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServerTest {

    public static void main(String[] args) throws IOException {
        //初始化服务端socket并且绑定 8080 端口
        ServerSocket serverSocket = new ServerSocket(8080);
        //循环监听客户端请求
        while (true) {
            try {
                //监听客户端请求
                Socket socket = serverSocket.accept();

                //将字节流转化成字符流，读取客户端输入的内容
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //读取一行数据
                String str = bufferedReader.readLine();
                //打印客户端发送的信息
                System.out.println("服务端收到客户端发送的信息：" + str);

                //向客户端返回信息，将字符转化成字节流，并输出
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                printWriter.println("hello，我是服务端，已收到消息");

                // 关闭流
                bufferedReader.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}