package com.waben.stock.interfaces.service.manage;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 轮播 公共接口
 * 
 * @author luomengan
 *
 */
public interface BannerInterface {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	Response<List<BannerDto>> fetchBanners(@RequestParam(value = "enable",required = false) Boolean enable);

}
