package com.hong.util.date20200612;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态使用类
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年06月12日
 */
public class Uuser {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Uuser user = new Uuser();
        user.setName("Daisy");
        // System.out.println("User："+JSON.toJSONString(user));

        System.out.println("User："+ new JSONObject(user));
        Map<String,Object> propertiesMap = new HashMap<>(16, 1);
        propertiesMap.put("age", 18);
        Object obj = ReflectUtil.getObject(user, propertiesMap);
        System.out.println("动态为User添加age之后，User："+ new JSONObject(obj));
        // System.out.println("动态为User添加age之后，User："+JSON.toJSONString(obj));
    }
}
