package com.hong.test.date20200423;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码 并将验证码转化为图片的业务
 * href: https://blog.csdn.net/qq_40542534/article/details/108503921
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年09月10日
 */
public class VerificationCode {

    public VerificationCode() {
    }

    /**
     * 生成验证码字符串 -- 随机数
     */
    public static String createRandomCode() {
        //所有随机数字符串集合
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        //Random类 产生指定范围内的随机数
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        //随机截取4个字符随机数
        for (int i = 0; i < 4; i++) {
            //从str里面随机拿取一个整数
            int index = random.nextInt(str.length());
            //安装随机产生的值获取字符
            char randomStr = str.charAt(index);
            //StringBuilder拼接字符串
            sb.append(randomStr);
        }
        return sb.toString();
    }

    /**
     * 将生成的随机字符串验证码转化为图片
     */
    public static BufferedImage changeStringToImage(String code) {
        Random rd = new Random();
        //创建一个画布
        BufferedImage image = new BufferedImage(75, 28, BufferedImage.TYPE_INT_RGB);
        //创建画笔
        Graphics g = image.getGraphics();
        //给画笔设置颜色(绘制随机验证码的时候的验证码颜色)
        //#00000   #FFFFFF
        g.setColor(new Color(240, 240, 240));
        //设置验证码的 背景色
        g.fillRect(0, 0, 75, 28);
        // 设置字体
        g.setFont(new Font("宋体", Font.BOLD, 16));

        //#00000   #FFFFFF
        g.setColor(new Color(0, 0, 0));
        // g.drawString(checkCodeStr, 20, 20);
        for (int i = 0; i < 4; i++) {
            //画字符
            g.setColor(new Color(rd.nextInt(120), rd.nextInt(120), rd.nextInt(120)));
            g.drawString(code.charAt(i) + "", 16 * i + rd.nextInt(16), 15 + rd.nextInt(10));
            if (i % 2 == 0) {
                //画线
                g.setColor(new Color(rd.nextInt(120), rd.nextInt(120), rd.nextInt(120)));
                g.drawLine(rd.nextInt(75), rd.nextInt(28), rd.nextInt(75), rd.nextInt(28));
            }
        }
        return image;
    }
}

