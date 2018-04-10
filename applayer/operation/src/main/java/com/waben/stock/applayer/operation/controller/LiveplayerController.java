package com.waben.stock.applayer.operation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/liveplayer")
public class LiveplayerController {
	
	@RequestMapping("/index")
	public String index() {
		return "manage/liveplayer/index";
	}
	
}
