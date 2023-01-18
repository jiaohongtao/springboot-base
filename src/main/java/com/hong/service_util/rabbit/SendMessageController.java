package com.hong.service_util.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
 
/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@RestController
public class SendMessageController {
 
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法
 
    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }


    @GetMapping("/sendCmpUserCmdbEvent")
    public String sendCmpUserCmdbEvent() {
        String userJson = "{\"id\":\"7666baa1-7e4d-4619-9a93-5d1bfdc9f411\",\"tenantId\":\"default\",\"loginId\":\"admin\",\"name\":\"平台管理员\",\"is_admin\":false,\"email\":\"admin@cmp.com\",\"phone\":\"\",\"qqNumber\":\"\",\"wechat\":\"\",\"dingTalk\":\"\",\"businessGroupName\":\"test1,test2\",\"businessGroupList\":[{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"test1\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true},{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"test2\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true}]}";
        Map<String,Object> map=new HashMap<>();
        map.put("event_type","create");
        map.put("entity_class","user");
        map.put("data",userJson);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("CmpDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/updateCmpUserCmdbEvent")
    public String updateCmpUserCmdbEvent() {
        String userJson = "{\"id\":\"7666baa1-7e4d-4619-9a93-5d1bfdc9f411\",\"tenantId\":\"default\",\"loginId\":\"admin\",\"name\":\"我是平台管理员\",\"is_admin\":false,\"email\":\"admin@cmp.com\",\"phone\":\"183123456\",\"qqNumber\":\"666666\",\"wechat\":\"\",\"dingTalk\":\"\",\"businessGroupName\":\"test1,test2\",\"businessGroupList\":[{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"test1\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true},{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"test2\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true}]}";
        Map<String,Object> map=new HashMap<>();
        map.put("event_type","update");
        map.put("entity_class","user");
        map.put("data",userJson);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("CmpDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/deleteCmpUserCmdbEvent")
    public String deleteCmpUserCmdbEvent() {
        String userJson = "{\"id\":\"7666baa1-7e4d-4619-9a93-5d1bfdc9f411\",\"tenantId\":\"default\",\"loginId\":\"admin\",\"name\":\"我是平台管理员\",\"is_admin\":false,\"email\":\"admin@cmp.com\",\"phone\":\"183123456\",\"qqNumber\":\"666666\",\"wechat\":\"\",\"dingTalk\":\"\",\"businessGroupName\":\"test1,test2\",\"businessGroupList\":[{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"test1\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true},{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"test2\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true}]}";
        Map<String,Object> map=new HashMap<>();
        map.put("event_type","delete");
        map.put("entity_class","user");
        map.put("data",userJson);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("CmpDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/sendCmpProjectCmdbEvent")
    public String sendCmpProjectCmdbEvent() {
        String projectJson = "{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"yang1\",\"description\":\"\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true}";
        Map<String,Object> map=new HashMap<>();
        map.put("event_type","create");
        map.put("entity_class","business_group");
        map.put("data",projectJson);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("CmpDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/updateProjectCmdbEvent")
    public String updateProjectCmdbEvent() {
        String projectJson = "{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"yang1\",\"description\":\"测试修改\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true}";
        Map<String,Object> map=new HashMap<>();
        map.put("event_type","update");
        map.put("entity_class","business_group");
        map.put("data",projectJson);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("CmpDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/deleteProjectCmdbEvent")
    public String deleteProjectCmdbEvent() {
        String projectJson = "{\"id\":\"85c1b749-f00a-4443-a402-6ab2c0e37025\",\"tenantId\":\"default\",\"name\":\"yang1\",\"description\":\"测试修改\",\"parentBusinessGroupId\":\"\",\"parentBusinessGroupName\":\"\",\"level\":0,\"rootLevel\":true}";
        Map<String,Object> map=new HashMap<>();
        map.put("event_type","delete");
        map.put("entity_class","business_group");
        map.put("data",projectJson);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("CmpDirectExchange", "TestDirectRouting", map);
        return "ok";
    }
 
}