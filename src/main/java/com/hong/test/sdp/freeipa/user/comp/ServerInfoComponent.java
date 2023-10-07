package com.hong.test.sdp.freeipa.user.comp;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/19
 */
@Component
public class ServerInfoComponent {

    public JSONObject get() {
        JSONObject serverInfo = new JSONObject();
        serverInfo.put("domain", "sdp246.hadoop.com");
        serverInfo.put("username", "admin");
        serverInfo.put("password", "mima123456");
        return serverInfo;
    }

    public JSONObject get(Integer clusterId, String type) {
        JSONObject serverInfo = new JSONObject();
        serverInfo.put("domain", "sdp246.hadoop.com");
        serverInfo.put("username", "admin");
        serverInfo.put("password", "mima123456");
        return serverInfo;
    }

}
