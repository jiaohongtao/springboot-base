package com.hong.util;// 需要用户名密码邮件发送，你需要在qq后台设置

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class QQSendEmail {
    public static void main(String[] args) {
        // 收件人电子邮箱
        String to = "xxx@qq.com";
        // to = "hongtao.jiao@seaboxdata.com";
        // to = "xxx@163.com";

        // 发件人电子邮箱
        String from = "xxxxxx@qq.com";

        // String security = "rtusslldorcpbfdd";
        String security = "rtusslldorcpbfdd";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "jfqrtdxqinxxjecg"); //发件人邮件用户名、授权码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            // message.setSubject("This is the Subject Line!");
            // message.setSubject("是这个邮件吗？");
            message.setSubject("加薪通知");

            // 设置消息体
            // message.setText("This is actual message");
            message.setText("XX同学您好，由于您最近的表现极佳，特给您加薪5k！");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
