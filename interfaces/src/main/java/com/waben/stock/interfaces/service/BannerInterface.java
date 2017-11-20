package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 轮播 公共接口
 * 
 * @author luomengan
 *
 */
public interface BannerInterface {

	@RequestMapping(value = "/getEnabledBannerList", method = RequestMethod.GET)
	Response<List<BannerDto>> getEnabledBannerList();

}
