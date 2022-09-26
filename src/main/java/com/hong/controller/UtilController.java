package com.hong.controller;

import cn.hutool.json.JSONArray;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Util Controller
 * href: https://api.uomg.com
 * href: https://api.vvhan.com
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
        String url = "https://api.uomg.com/api/rand.music";
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

    @GetMapping("/wallpaper")
    @ApiOperation(value = "图片解析 - LOFTER（乐乎）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "format", value = "[json|text|images]")
    })
    public Result wallpaper(@RequestParam(required = false) String format) {
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
    public String heartWord(@RequestParam(required = false) String aa1) {
        // https://v.api.aa1.cn/api/api-wenan-qg/index.php?aa1=json
        String url = "https://v.api.aa1.cn/api/api-wenan-qg/index.php";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("aa1", StringUtils.isBlank(aa1) ? "json" : aa1));
        String result = HttpClientUitl.doHttpGet(url, params);
        if (JSONUtil.isJsonArray(result)) {
            JSONArray jsonResult = JSONUtil.parseArray(result);
            return jsonResult.getJSONObject(0).getStr("qinggan");
        }
        return result;
    }

    @GetMapping("/qqAvatar")
    @ApiOperation(value = "qq头像", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qq", value = "[qq号]")
    })
    public Result qqAvatar(@RequestParam String qq) {
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
    public Result weather(@RequestParam String city, @RequestParam(required = false) String type) {
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
            /*JSONObject jsonData = jsonResult.getJSONObject("data");

            // 今天
            String cityName = jsonData.getStr("city");
            String ganmao = jsonData.getStr("ganmao");
            String wendu = jsonData.getStr("wendu");

            // 前一天
            JSONObject yesterdayJson = jsonData.getJSONObject("yesterday");
            String yData = yesterdayJson.getStr("data");
            String yHigh = yesterdayJson.getStr("high");
            String yFx = yesterdayJson.getStr("fx");
            String yLow = yesterdayJson.getStr("low");
            String yFl = yesterdayJson.getStr("fl");

            // 从今天开始五天
            List<JSONObject> forecastList = jsonData.getJSONArray("forecast").toList(JSONObject.class);
            forecastList.forEach(forecast -> {
                String date = forecast.getStr("date");
                String high = forecast.getStr("high");
                String fengli = forecast.getStr("fengli");
                String low = forecast.getStr("low");
                String fengxiang = forecast.getStr("fengxiang");
                String forecastType = forecast.getStr("type");
            });*/

        } else {
            // 天
            Boolean success = jsonResult.getBool("success");
            if (!success) {
                log.info("{}", jsonResult);
                return Result.failed("没有该城市");
            }

            /*String resultCity = jsonResult.getStr("city");
            JSONObject resultInfo = jsonResult.getJSONObject("info");
            String date = resultInfo.getStr("data");
            String weatherType = resultInfo.getStr("type");
            String weatherHigh = resultInfo.getStr("high");
            String weatherLow = resultInfo.getStr("low");
            String weatherFengxiang = resultInfo.getStr("fengxiang");
            String weatherFengli = resultInfo.getStr("fengli");
            String weatherTip = resultInfo.getStr("tip");*/
        }

        // String returnResult = date + weatherType + weatherHigh + weatherLow + weatherFengxiang + weatherFengli + weatherTip;
        // return Result.success(result);
        return Result.success(jsonResult);
    }

    /*@GetMapping("/weiboHot")
    @ApiOperation(value = "微博热搜", httpMethod = "GET")
    public Result weiboHot() {
        // 32ffd8fc28e5d36bdc6e807f3a0f3b6e
        // http://api.tianapi.com/weibohot/index?key=APIKEY

        //java环境中文传值时，需特别注意字符编码问题
        String httpUrl = "http://api.tianapi.com/weibohot/index";
        String jsonResult = requestHasKey(httpUrl);
        return Result.success(jsonResult);
    }*/

    @GetMapping("/kuaidi")
    @ApiOperation(value = "快递查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "快递公司代号 [申通=shentong EMS=ems 顺丰=shunfeng 圆通=yuantong 中通=zhongtong 韵达=yunda" +
                    " 天天=tiantian 汇通=huitongkuaidi 全峰=quanfengkuaidi 德邦=debangwuliu 宅急送=zhaijisong]"),
            @ApiImplicitParam(name = "postId", value = "快递单号")
    })
    public Result kuaidi(@RequestParam String type, @RequestParam String postId) {
        // http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
        // PS：快递公司编码:申通="shentong" EMS="ems" 顺丰="shunfeng" 圆通="yuantong" 中通="zhongtong" 韵达="yunda"
        // 天天="tiantian" 汇通="huitongkuaidi" 全峰="quanfengkuaidi" 德邦="debangwuliu" 宅急送="zhaijisong"

        String httpUrl = "http://www.kuaidi100.com/query?type=" + type + "&postid=" + postId;
        String jsonResult = requestHasKey(httpUrl);
        return Result.success(jsonResult);
    }


    /**
     * @param httpUrl 请求接口
     * @return 返回结果
     */
    public static String requestHasKey(String httpUrl) {
        return requestArg(httpUrl, "key=32ffd8fc28e5d36bdc6e807f3a0f3b6e");
    }

    /**
     * @param httpUrl 请求接口
     * @return 返回结果
     */
    public static String request(String httpUrl) {
        return requestArg(httpUrl, "");
    }

    /**
     * @param httpUrl 请求接口
     * @param httpArg 参数
     * @return 返回结果
     */
    public static String requestArg(String httpUrl, String httpArg) {
        BufferedReader reader;
        String result = null;
        StringBuilder stringBuilder = new StringBuilder();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                stringBuilder.append(strRead);
                stringBuilder.append("\r\n");
            }
            reader.close();
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
