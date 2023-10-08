package com.hong.controller;

import com.hong.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author jiaohongtao
 * @since 2019/8/22 16:39
 */
@RestController
@Api(tags = "hello哇")
@Validated
public class HelloController {

    /*@Value("${my.name}")
    private String name;

    @GetMapping({"", "/index"})
    @ApiOperation(value = "首页(hello)", httpMethod = "GET")
    public String index() {
        return "hello:" + name;
    }*/

    @GetMapping("/hello")
    @ApiOperation(value = "Hello", httpMethod = "GET")
    public String hello(String name) {
        name = StringUtils.isBlank(name) ? "brother" : name;
        return "hello," + name;
    }

    @GetMapping("/helloName")
    @ApiOperation(value = "HelloName", httpMethod = "GET")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "name", paramType = "query", required = true)
    )
    public Result helloName(@NotNull(message = "名字不能为空") String name) {
        name = StringUtils.isBlank(name) ? "brother" : name;
        return Result.success("hello," + name);
    }
}