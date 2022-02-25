package com.hong.controller;

import com.hong.annotation.AccessLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/02/25
 */
@RestController
@RequestMapping("/access/limit")
public class AccessLimitController {

    //15秒内 允许请求3次
    @GetMapping("test")
    @AccessLimit(seconds = 15, maxCnt = 3)
    public String testAccessLimit() {
        return "success";
    }
}
