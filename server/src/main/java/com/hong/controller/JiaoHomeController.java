package com.hong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年01月29日
 */
@Controller
@Api(tags = "家谱")
public class JiaoHomeController {
    @GetMapping("/jinjin/jiao")
    @ApiOperation(value = "我的家谱", httpMethod = "GET")
    public String jiaoHome() {
        return "jiaoHome";
    }
}
