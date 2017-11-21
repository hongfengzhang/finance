package com.waben.stock.applayer.tactics.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.service.BannerService;
import com.waben.stock.applayer.tactics.service.CircularsService;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.dto.system.AppHomeTopDataDto;
import com.waben.stock.interfaces.dto.system.StockMarketIndex;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.ApiOperation;

/**
 * @author Created by yuyidi on 2017/11/7.
 * @desc
 */
@RestController
@RequestMapping("/system")
@RefreshScope
public class SystemController {

	@Value("${spring.jpa.show-sql:error}")
	private String show;

	@GetMapping("/index")
	public String index() {
		return show;
	}

	@Autowired
	private BannerService bannerService;

	@Autowired
	private CircularsService circularsService;

	@GetMapping("/getEnabledBannerList")
	@ApiOperation(value = "获取轮播图列表")
	public Response<List<BannerDto>> getBannerList() {
		return bannerService.getByState(true);
	}

	@GetMapping("/getEnabledCircularsList")
	@ApiOperation(value = "获取通告列表")
	public Response<List<CircularsDto>> getCircularsList() {
		return circularsService.getByEnable(true);
	}

	@GetMapping("/getAppHomeTopData")
	@ApiOperation(value = "获取APP首页顶部数据（轮播图、公告、大盘指数）")
	public Response<AppHomeTopDataDto> getAppHomeTopData() {
		Response<AppHomeTopDataDto> result = new Response<>(new AppHomeTopDataDto());
		// 获取轮播图
		Response<List<BannerDto>> bannerListResp = bannerService.getByState(true);
		if ("200".equals(bannerListResp.getCode())) {
			result.getResult().setBannerList(bannerListResp.getResult());
		} else {
			result.setCode(bannerListResp.getCode());
			result.setMessage(bannerListResp.getMessage());
			return result;
		}
		// 获取公告
		Response<List<CircularsDto>> capitalAccountResp = circularsService.getByEnable(true);
		if ("200".equals(capitalAccountResp.getCode())) {
			result.getResult().setCircularsList(capitalAccountResp.getResult());
		} else {
			result.setCode(capitalAccountResp.getCode());
			result.setMessage(capitalAccountResp.getMessage());
			return result;
		}
		// 获取股票市场指数
		StockMarketIndex stockMarketIndex = new StockMarketIndex();
		stockMarketIndex.setSzzs(new BigDecimal(3410.50), new BigDecimal(3392.40));
		stockMarketIndex.setSzcz(new BigDecimal(11602.10), new BigDecimal(11437.55));
		stockMarketIndex.setCybz(new BigDecimal(1856.96), new BigDecimal(1859.75));
		result.getResult().setStockMarketIndex(stockMarketIndex);
		return result;
	}

}
