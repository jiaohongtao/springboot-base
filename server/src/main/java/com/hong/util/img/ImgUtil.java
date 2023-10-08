package com.hong.util.img;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Constant;
import com.hong.util.httpRequest.HttpClientUtil;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

/**
 * 图片工具
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/28
 */
public class ImgUtil {

    public static final String CODE_URL = "http://10.1.3.150:8000/prod-api/code";

    public static void main(String[] args) {
        String imgSavePath = Constant.DESKTOP_PATH + "\\image.jpg";

        String imgBase64 = getImg();
        saveImgFile(imgBase64, imgSavePath);
        openImg(imgSavePath);
        keyboardIn();
    }

    public static String getImg() {
        String result = HttpClientUtil.doHttpGet(CODE_URL, null);
        JSONObject json = JSONObject.parseObject(result);
        return json.getString("img");
    }

    public static JSONObject getImgInfo() {
        String result = HttpClientUtil.doHttpGet(CODE_URL, null);
        return JSONObject.parseObject(result);
    }

    public static void saveImgFile(String imgBase64, String imgSavePath) {
        // 解码Base64字符串为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(imgBase64);

        // 将字节数组保存为图片文件
        try (FileOutputStream outputStream = new FileOutputStream(imgSavePath)) {
            outputStream.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keyboardIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串");

        String input = scanner.nextLine();
        System.out.println("输入的是：" + input);
        scanner.close();
    }

    public static String getScan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入验证码结果");

        String input = scanner.nextLine();
        System.out.println("输入的是：" + input);
        scanner.close();
        return input;
    }

    public static void openImg(String imgPath) {
        File imageFile = new File(imgPath);
        try {
            if (imageFile.exists()) {
                Desktop.getDesktop().open(imageFile);
            } else {
                System.out.println("图片文件不存在！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
