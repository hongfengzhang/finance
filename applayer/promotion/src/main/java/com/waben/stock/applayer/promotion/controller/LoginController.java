package com.waben.stock.applayer.promotion.controller;

import javax.servlet.http.HttpServletResponse;

import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.MenuDto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
@RequestMapping("/")
public class LoginController {

	@GetMapping("/login")
	public String login(HttpServletResponse response) {
		return "forward:/pages/user/login.html";
	}

	@GetMapping("/menus")
	@ResponseBody
	public List<MenuDto> fetch(HttpServletResponse response) {
		List<MenuDto> menus = SecurityAccount.current().getMenus();
		return menus;
	}

}
