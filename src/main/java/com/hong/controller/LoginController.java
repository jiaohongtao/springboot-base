package com.hong.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/05/23
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ModelAndView getLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // User user = userService.validateUser(username, password);
        Map<String, Object> user = new HashMap<>();
        user.put("username", "jiao");
        user.put("password", "123456");

        // 有一项不匹配，则获取用户失败
        if (!user.get("username").equals(username) || !user.get("password").equals(password)) {
            user = null;
        }

        if (user == null) {
            return new ModelAndView("errorLogin", "errorMessage", "Invalid Credentials!");
        }
        user.remove("password");
        session.setAttribute("user", user);
        return new ModelAndView("redirect:/dashboard");
    }

}
