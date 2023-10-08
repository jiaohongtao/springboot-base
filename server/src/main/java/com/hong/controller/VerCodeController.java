package com.hong.controller;

import com.hong.test.date20200423.VerificationCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年09月10日
 */
@RestController
public class VerCodeController {

    @GetMapping("/verCode")
    public void getVerCode(HttpServletRequest request, HttpServletResponse response) {
        //1:创建生产一个随机的4个字符组成的字符串
        String code = VerificationCode.createRandomCode();
        System.out.println(code);
        //2:将字符串转成图片
        //BufferedImage类将图片生成到内存中，然后直接发送给浏览器
        BufferedImage image = VerificationCode.changeStringToImage(code);
        //3:使用OutputStream写到浏览器
        System.out.println(image);

        //参1，内存中的图片  参2，格式  参3，字节输出流
        try {
            ImageIO.write(image, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value="/verCodePage")
    public ModelAndView login(){
        return new ModelAndView("verCode");
    }
}
