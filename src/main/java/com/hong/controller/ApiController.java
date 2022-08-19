package com.hong.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hong.bean.Constant;
import com.hong.bean.Result;
import com.hong.util.common.ClientIdUtil;
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
import java.util.List;

/**
 * Api Controller
 * href: https://api.uomg.com
 * href: https://api.vvhan.com
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/09
 */
@Slf4j
@Api(tags = "API")
@RestController
@RequestMapping("/api")
public class ApiController {

    // sort	否	string	选择输出分类[男|女|动漫男|动漫女]，为空随机输出
    // 	format	否	string	选择输出格式[json|images]
    @GetMapping("/avatar")
    @ApiOperation(value = "获取头像", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "[男|女|动漫男|动漫女]"),
            @ApiImplicitParam(name = "format", value = "[json|images]")
    })
    public Result getAvatar(@RequestParam String clientId, @RequestParam String sort, @RequestParam String format) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

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
    public Result loveTalk(@RequestParam String clientId, @RequestParam(required = false) String format) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

        String url = "https://api.uomg.com/api/rand.qinghua";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("format", StringUtils.isBlank(format) ? "json" : format));
        String result = HttpClientUitl.doHttpGet(url, params);
        boolean json = JSONUtil.isJson(result);

        return Result.success(json ? JSONUtil.parseObj(result).getStr("content") : result);
    }

    @GetMapping("/myInfo")
    @ApiOperation(value = "访问者信息", httpMethod = "GET")
    public Result myInfo(@RequestParam String clientId) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

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
    public Result wyyComment(@RequestParam String clientId, @RequestParam(required = false) Integer mid, @RequestParam(required = false) String format) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

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
        return Result.success(result);
    }

    @GetMapping("/wallpaper")
    @ApiOperation(value = "图片解析 - LOFTER（乐乎）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "format", value = "[json|text|images]")
    })
    public Result wallpaper(@RequestParam String clientId, @RequestParam(required = false) String format) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

        // https://api.uomg.com/api/image.lofter?format=text
        String url = "https://api.uomg.com/api/image.lofter";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("format", StringUtils.isBlank(format) ? "json" : format));
        String result = HttpClientUitl.doHttpGet(url, params);
        if (JSONUtil.isJson(result)) {
            JSONObject jsonResult = JSONUtil.parseObj(result);
            Integer code = jsonResult.getInt("code");

            return code == 1 ? Result.success(jsonResult.getStr("data")) : Result.failed(jsonResult.getStr("msg"));
        }
        return Result.success(result);
    }

    @GetMapping("/heartWord")
    @ApiOperation(value = "情感一言", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aa1", value = "[json|text]")
    })
    public Result heartWord(@RequestParam String clientId, @RequestParam(required = false) String aa1) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

        // https://v.api.aa1.cn/api/api-wenan-qg/index.php?aa1=json
        String url = "https://v.api.aa1.cn/api/api-wenan-qg/index.php";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("aa1", StringUtils.isBlank(aa1) ? "json" : aa1));
        String result = HttpClientUitl.doHttpGet(url, params);
        if (JSONUtil.isJsonArray(result)) {
            JSONArray jsonResult = JSONUtil.parseArray(result);
            return Result.success(jsonResult.getJSONObject(0).getStr("qinggan"));
        }
        return Result.success(result);
    }

    @GetMapping("/qqAvatar")
    @ApiOperation(value = "qq头像", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qq", value = "[qq号]")
    })
    public Result qqAvatar(@RequestParam String clientId, @RequestParam String qq) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

        // https://v.api.aa1.cn/api/qqjson/index.php?qq=15001904
        String url = "https://v.api.aa1.cn/api/qqjson/index.php";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("qq", qq));
        String result = HttpClientUitl.doHttpGet(url, params);
        String json = result.substring(result.indexOf("["));
        return Result.success(json);
    }

    @GetMapping("/weather")
    @ApiOperation(value = "全国天气", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", value = "[城市|河北|邢台]"),
            @ApiImplicitParam(name = "type", value = "[week|空]")
    })
    public Result weather(@RequestParam String clientId, @RequestParam String city, @RequestParam(required = false) String type) {
        Result matchResult = ClientIdUtil.isMatchSuccess(clientId);
        if (!matchResult.isSuccess()) {
            return matchResult;
        }

        // https://api.vvhan.com/api/weather?city=徐州&type=week
        String url = "https://api.vvhan.com/api/weather";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("city", city));
        if (StringUtils.isNotBlank(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        String result = HttpClientUitl.doHttpGet(url, params);
        JSONObject jsonResult = JSONUtil.parseObj(result);

        if (StringUtils.isNotBlank(type)) {
            // 周
            Integer status = jsonResult.getInt("status");
            if (!status.equals(1000)) {
                log.info("{}", jsonResult);
                return Result.failed("没有该城市");
            }
        } else {
            // 天
            Boolean success = jsonResult.getBool("success");
            if (!success) {
                log.info("{}", jsonResult);
                return Result.failed("没有该城市");
            }
        }

        return Result.success(result);
    }

}
