package com.hong.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 通用数据Controller
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/26
 */
@RestController
@RequestMapping("stand")
public class StandController {

    @GetMapping("json")
    public JSONObject json() {
        return new JSONObject();
    }

    @GetMapping("jsonString")
    public String jsonString() {
        return "{}";
    }

    @GetMapping("array")
    public JSONArray array() {
        return new JSONArray();
    }

    @GetMapping("arrayString")
    public String arrayString() {
        return "[]";
    }

    @GetMapping("text")
    public String text() {
        return "text";
    }

    @GetMapping("page")
    public ModelAndView page() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("a", "a");
        return new ModelAndView("page/standPage", map);
    }
}
