package com.hong.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 显示 打包的 git 等信息
 * https://blog.csdn.net/mytt_10566/article/details/100116670
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/27
 */
@RestController
@RequestMapping("/pkg-version")
@Api(tags = "打包信息")
@Slf4j
public class ActuatorController {


    @ApiOperation(value = "详细信息", httpMethod = "GET")
    @GetMapping("/info")
    public Map<String, Object> getVersionInfo() {
        return readGitProperties();
    }

    private Map<String, Object> readGitProperties() {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            inputStream = classLoader.getResourceAsStream("git.properties");

            StringBuilder versionJson = new StringBuilder();
            if (inputStream != null) {

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                while (bufferedReader.ready()) {
                    String addresses = bufferedReader.readLine();
                    versionJson.append(addresses);
                }
            }

            if (StringUtils.isBlank(versionJson)) {
                versionJson.append("{'错误':'需打包后才可显示正常信息'}");
            }

            // 读取文件内容，自定义一个方法实现即可
            // String versionJson = FileUtils.getStringFromStream(inputStream);
            JSONObject jsonObject = JSONUtil.parseObj(versionJson);
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            if (CollectionUtils.isNotEmpty(entrySet)) {
                return entrySet.stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n));
            }
        } catch (Exception e) {
            log.error("get git version info fail", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("close inputstream fail", e);
            }
        }
        return new HashMap<>();
    }
}
