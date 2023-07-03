package com.hong.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/05/23
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {

    @PostMapping
    public ModelAndView getLogoutPage() {
        return new ModelAndView("logout");
    }

    @GetMapping
    public ModelAndView logout(HttpSession session) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "jiao");
        // user.put("password", "123456");

        Map<String, Object> userSessionMap = (Map<String, Object>) session.getAttribute("user");

        // 有一项不匹配，则获取用户失败
        if (!user.get("username").equals(userSessionMap.get("username"))) {
            user = null;
        }

        if (user == null) {
            return new ModelAndView("error", "errorMessage", "Invalid Credentials!");
        }
        session.invalidate();
        return new ModelAndView("redirect:/page/logout");
    }

}
