package com.hong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 所有页面跳转
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年12月24日
 */
@Controller
@Api(tags = "页面")
@RequestMapping("/page")
public class PageController {

    @ApiOperation(value = "获取身份证信息页面", httpMethod = "GET")
    @GetMapping("/idCard")
    public String idCard() {
        return "idCard";
    }

    @ApiOperation(value = "上传文件页面", httpMethod = "GET")
    @GetMapping("/file")
    public String file() {
        return "fileRead";
    }

    @ApiOperation(value = "天气页面", httpMethod = "GET")
    @GetMapping("/weather")
    public String weather() {
        return "weather";
    }

    @ApiOperation(value = "testApi", httpMethod = "GET")
    @GetMapping("/testApi")
    public String testApi() {
        return "testApi";
    }

    @ApiOperation(value = "/api", httpMethod = "GET")
    @GetMapping("/api")
    public String api() {
        return "apiDoc";
    }

    @ApiOperation(value = "/loveTalk", httpMethod = "GET")
    @GetMapping("/loveTalk")
    public String loveTalk() {
        return "apiPage/loveTalk";
    }

    @ApiOperation(value = "/toPage", httpMethod = "GET")
    @GetMapping("/toPage/{pageName}")
    public String loveTalk(@PathVariable String pageName) {
        return "page/" + pageName;
    }

    @ApiOperation(value = "toPage", httpMethod = "GET")
    @GetMapping("/{pageName}")
    public String toPage(@PathVariable String pageName) {
        return "page/" + pageName;
    }
}
