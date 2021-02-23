package com.hong.controller;

import com.hong.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件操作
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年02月23日
 */
@Api(tags = "文件操作")
@RestController
@RequestMapping("/file")
public class FileReadController {

    @PostMapping("/read")
    @ApiOperation(value = "读取文件")
    public Result readFile(@RequestParam("fileName") MultipartFile file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(stringBuilder.toString());
    }
}
