package com.waben.stock.interfaces.service.manage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.manage.AppVersionDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * app版本 公共接口
 * 
 * @author luomengan
 *
 */
public interface AppVersionInterface {

	@RequestMapping(value = "/currentAppVersion", method = RequestMethod.GET)
	Response<AppVersionDto> getCurrentAppVersion();

}
