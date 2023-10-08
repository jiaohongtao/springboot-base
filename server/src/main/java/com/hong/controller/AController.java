package com.hong.controller;

import com.hong.annotation.Log;
import com.hong.enums.JhtService;
import com.hong.service.interf.AService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试AService实现类
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年07月24日
 */
@RestController
@Api("测试AService实现类")
@RequestMapping("a")
public class AController {

    @Autowired
    @Qualifier("aOneServiceImpl")
    AService oneService;

    /*@Autowired
    @Qualifier("aTwoServiceImpl")
    AService twoService;*/
    @Resource(name = "aTwoServiceImpl")
    AService twoService;

    @Log(module = JhtService.BASE, target = "测试", operation = "获取one")
    @GetMapping("one")
    @ApiOperation(value = "one", httpMethod = "GET")
    public String aOneService() {
        return "我是" + oneService.a();
    }

    @PostMapping("/two")
    @ApiOperation(value = "two", httpMethod = "POST")
    public String aTwoService() {
        return "我是" + twoService.a();
    }
}
