package com.hong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/05/23
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    @GetMapping("get1")
    @ResponseBody
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("username", "user123");
        return "Session set successfully!";
    }

    @GetMapping("/get2")
    @ResponseBody
    public String getSession(HttpSession session) {
        session.setAttribute("username", "user123");
        return "Session set successfully!";
    }
}
