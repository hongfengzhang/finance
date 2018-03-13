package com.waben.stock.applayer.promotion.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

	@GetMapping("/login")
	public String login(HttpServletResponse response) {
		return "forward:/pages/user/login.html";
	}

}
