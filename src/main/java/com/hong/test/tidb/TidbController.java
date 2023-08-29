package com.hong.test.tidb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * href: https://www.dandelioncloud.cn/article/details/1607178892362334209
 */
@RequestMapping("/tidb")
@RestController
public class TidbController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public Object getList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "测试");
        return userService.selectList(queryWrapper);
    }
}