package com.hong.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hong.bean.Constant;
import com.hong.bean.Result;
import com.hong.util.httpRequest.HttpClientUitl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Util Controller
 * href: https://api.uomg.com
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/09
 */
@Slf4j
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
    // public Result loveTalk(@RequestParam(required = false) String format) {
    public String loveTalk(@RequestParam(required = false) String format) {
        String url = "https://api.uomg.com/api/rand.qinghua";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("format", StringUtils.isBlank(format) ? "json" : format));
        String result = HttpClientUitl.doHttpGet(url, params);
        boolean json = JSONUtil.isJson(result);

        // return Result.success(json ? JSONUtil.parseObj(result).getStr("content") : result);
        return json ? JSONUtil.parseObj(result).getStr("content") : result;
    }

    @GetMapping("/myInfo")
    @ApiOperation(value = "访问者信息", httpMethod = "GET")
    public Result myInfo() {
        String url = "https://api.uomg.com/api/visitor.info";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("skey", "随便"));
        String result = HttpClientUitl.doHttpGet(url, params);
        // boolean json = JSONUtil.isJson(result);

        return Result.success(result);
    }

    @GetMapping("/wyyComment")
    @ApiOperation(value = "网易云热评", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "网易云歌单ID"),
            @ApiImplicitParam(name = "format", value = "[json|text]")
    })
    // public Result wyyComment(@RequestParam(required = false) Integer mid, @RequestParam(required = false) String format) {
    public String wyyComment(@RequestParam(required = false) Integer mid, @RequestParam(required = false) String format) {
        String url = "https://api.uomg.com/api/comments.163";
        List<NameValuePair> params = new ArrayList<>();
        if (null != mid) {
            params.add(new BasicNameValuePair("mid", mid + ""));
        }
        params.add(new BasicNameValuePair("format", StringUtils.isBlank(format) ? "json" : format));
        String result = HttpClientUitl.doHttpGet(url, params);
        log.info("\n" + result);
        boolean json = JSONUtil.isJson(result);
        StringBuilder stringBuilder = new StringBuilder();
        if (json) {
            JSONObject jsonResult = JSONUtil.parseObj(result);
            JSONObject jsonData = jsonResult.getJSONObject("data");

            stringBuilder.append("歌名：").append(jsonData.getStr("name")).append(Constant.CONCAT_TO_SPLIT);
            stringBuilder.append("作者：").append(jsonData.getStr("artistsname")).append(Constant.CONCAT_TO_SPLIT);
            stringBuilder.append("作者头像：").append(jsonData.getStr("avatarurl")).append(Constant.CONCAT_TO_SPLIT);
            stringBuilder.append("歌曲地址：").append(jsonData.getStr("url")).append(Constant.CONCAT_TO_SPLIT);
            stringBuilder.append("热评人：").append(jsonData.getStr("nickname")).append(Constant.CONCAT_TO_SPLIT);
            stringBuilder.append("热评人头像：").append(jsonData.getStr("picurl")).append(Constant.CONCAT_TO_SPLIT);
            stringBuilder.append("热评：").append(jsonData.getStr("content")).append(Constant.CONCAT_TO_SPLIT);
            result = stringBuilder.toString();
        }
        // return Result.success(result);
        return result;
    }

}
