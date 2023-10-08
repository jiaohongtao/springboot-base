package com.hong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能说明Controller
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年09月09日
 */
@Api("功能说明")
@Controller
public class FunctionDescriptionController {

    @ApiOperation("跳转页面")
    // @GetMapping("/function/description")
    @GetMapping("/func")
    public String functionDescription() {
        return "functionDescription";
    }
}

