package com.waben.stock.datalayer.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.manage.service.BannerService;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.service.BannerInterface;

/**
 * 轮播 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/banner")
public class BannerController implements BannerInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BannerService bannerService;

	@Override
	public List<BannerDto> getEnabledBannerList() {
		return null;
	}
}
