package com.waben.stock.applayer.tactics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.AppVersionBusiness;
import com.waben.stock.interfaces.dto.manage.AppVersionDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * app版本 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/appversion")
@Api(description = "app版本")
public class AppVersionController {

	@Autowired
	private AppVersionBusiness business;

	@GetMapping("/currentAppVersion")
	@ApiOperation(value = "获取当前版本")
	public Response<AppVersionDto> getCurrentAppVersion() {
		return new Response<>(business.getCurrentAppVersion());
	}

}
