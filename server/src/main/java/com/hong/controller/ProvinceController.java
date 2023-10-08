package com.hong.controller;

import com.hong.util.common.IDCardUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年12月23日
 */
@Api(tags = "获取身份证信息")
@RestController
@RequestMapping("/idCard")
public class ProvinceController {
    @ApiOperation(value = "省级地区", httpMethod = "GET")
    @GetMapping("/province/{idNumber}")
    public String getProvince(@PathVariable String idNumber) {
        return IDCardUtil.getProvince(idNumber);
    }

    @ApiOperation(value = "县级地区", httpMethod = "GET")
    @GetMapping("/cityOrProvince/{idNumber}/{type}")
    public String getCity(@PathVariable String idNumber, @PathVariable int type) {
        return IDCardUtil.getCityOrProvince(idNumber, type);
    }

    @ApiOperation(value = "省市县地区", httpMethod = "GET")
    @GetMapping("/cityOrProvince/{idNumber}")
    public String getCity(@PathVariable String idNumber) {
        return IDCardUtil.getCity(idNumber);
    }

    @ApiOperation(value = "性别", httpMethod = "GET")
    @GetMapping("/sex/{idNumber}")
    public String getSex(@PathVariable String idNumber) {
        return IDCardUtil.judgeGender(idNumber);
    }

    @ApiOperation(value = "年龄", httpMethod = "GET")
    @GetMapping("/age/{idNumber}")
    public int getAge(@PathVariable String idNumber) {
        return IDCardUtil.countAge(idNumber);
    }
}
