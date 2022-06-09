package com.hong.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hong.bean.Result;
import com.hong.util.httpRequest.HttpClientUitl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Util Controller
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/09
 */
@Api(tags = "工具")
@RestController
@RequestMapping("/util")
public class UtilController {

    // sort	否	string	选择输出分类[男|女|动漫男|动漫女]，为空随机输出
    // 	format	否	string	选择输出格式[json|images]
    @GetMapping("/avatar")
    @ApiOperation(value = "获取头像", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "[男|女|动漫男|动漫女]"),
            @ApiImplicitParam(name = "format", value = "[json|images]")
    })
    public Result getAvatar(@RequestParam String sort, @RequestParam String format) {
        // https://api.uomg.com/api/rand.avatar?sort=男&format=json
        String url = "https://api.uomg.com/api/rand.avatar";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("sort", StringUtils.isBlank(sort) ? "男" : sort));
        params.add(new BasicNameValuePair("format", StringUtils.isBlank(format) ? "json" : format));
        String result = HttpClientUitl.doHttpGet(url, params);
        JSONObject jsonResult = JSONUtil.parseObj(result);
        Integer code = jsonResult.getInt("code");

        return code == 1 ? Result.success(jsonResult.getStr("imgurl")) : Result.failed(jsonResult.getStr("msg"));
    }

    @GetMapping("/loveTalk")
    @ApiOperation(value = "获取土味情话", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "format", value = "[json|text|js]")
    })
    public Result loveTalk(@RequestParam String format) {
        String url = "https://api.uomg.com/api/rand.qinghua";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("format", StringUtils.isBlank(format) ? "json" : format));
        String result = HttpClientUitl.doHttpGet(url, params);
        boolean json = JSONUtil.isJson(result);

        return Result.success(json ? JSONUtil.parseObj(result).getStr("content") : result);
    }
}
