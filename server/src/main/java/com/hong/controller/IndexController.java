package com.hong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年12月24日
 */
@Api(tags = "首页")
@Controller
public class IndexController {
    @GetMapping({"", "/index"})
    @ApiOperation(value = "首页", httpMethod = "GET")
    public String index() {
        return "index";
    }
}
