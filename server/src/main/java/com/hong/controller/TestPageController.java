package com.hong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年08月14日
 */
@Controller
@RequestMapping("page")
@Api("TestPageController")
public class TestPageController {

    @GetMapping("hello2")
    @ApiOperation(value = "page hello2", httpMethod = "GET")
    public String hello1() {
        return "hello2";
    }
}
