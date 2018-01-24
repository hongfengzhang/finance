package com.waben.stock.datalayer.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.manage.service.AppVersionService;
import com.waben.stock.interfaces.dto.manage.AppVersionDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.manage.AppVersionInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * app版本 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/appversion")
public class AppVersionController implements AppVersionInterface {

	@Autowired
	private AppVersionService service;

	@Override
	public Response<AppVersionDto> getCurrentAppVersion() {
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(AppVersionDto.class, service.getCurrentAppVersion(), false));
	}

}
