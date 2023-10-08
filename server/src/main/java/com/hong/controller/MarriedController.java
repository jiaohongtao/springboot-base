package com.hong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我们结婚了
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年07月24日
 */
@Controller
@Api
public class MarriedController {

    @GetMapping("married")
    @ApiOperation(value = "我们结婚了", httpMethod = "GET")
    public String married() {
        return "married";
    }
}
