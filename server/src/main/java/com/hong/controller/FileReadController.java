package com.hong.controller;

import com.hong.bean.Result;
import com.hong.util.common.IDCardUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/idCard/analyze")
    @ApiOperation(value = "身份证号地址识别")
    public Result idCardAnalyze(@RequestParam("fileName") MultipartFile file) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Map<String, String>> personMsgList = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                if (StringUtils.isNotBlank(string)) {
                    String[] nameIdCard = string.split(" ");
                    String name = nameIdCard[0];
                    String idCard = nameIdCard[1];

                    String city = IDCardUtil.getCity(idCard);
                    stringBuilder.append(name).append("\t").append(city).append("\t");
                    stringBuilder.append(idCard).append("\n");

                    // 添加到list中
                    Map<String, String> person = new HashMap<>();
                    person.put("name", name);
                    person.put("city", city);
                    person.put("idCard", idCard);
                    personMsgList.add(person);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return Result.success(stringBuilder.toString());
        return Result.success(personMsgList);
    }
}
