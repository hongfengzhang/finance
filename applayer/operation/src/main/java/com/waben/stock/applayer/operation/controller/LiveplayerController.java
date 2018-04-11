package com.waben.stock.applayer.operation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.waben.stock.interfaces.commonapi.netease.bean.NeteaseChannellistRet;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

@Controller
@RequestMapping("/liveplayer")
public class LiveplayerController {

	@RequestMapping("/index")
	public String index() {
		return "manage/liveplayer/index";
	}

	public Response<PageInfo<NeteaseChannellistRet>> livePlayerList(int page, int size) {
		
		return null;
	}

}
