package com.hong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年08月14日
 */
@Controller
public class SocketController {

    @GetMapping("/webSocket")
    public ModelAndView socket() {
        ModelAndView mav = new ModelAndView("/webSocket");
        mav.addObject("ha", "en?");
        return mav;
    }
}