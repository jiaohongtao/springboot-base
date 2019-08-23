package com.example.springboot_html.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 焦洪涛
 * @since 2019/8/22 16:39
 */
@RestController
public class HelloController {
	@RequestMapping("/index")
	public String index() {
		return "hello";
	}
}
