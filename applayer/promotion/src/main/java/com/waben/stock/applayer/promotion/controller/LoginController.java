package com.waben.stock.applayer.promotion.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginController {

	@GetMapping("/login")
	public String login(HttpServletResponse response) {
		return "forward:/pages/user/login.html";
	}

	@RequestMapping("/echo")
	@ResponseBody
	@PreAuthorize("hasRole('ORG_VIEW')")
	public String echo() {
		return "echo";
	}

	@RequestMapping("/manage")
	@ResponseBody
	@PreAuthorize("hasRole('ORG_MANAGE')")
	public String manage() {
		return "manage";
	}
}
