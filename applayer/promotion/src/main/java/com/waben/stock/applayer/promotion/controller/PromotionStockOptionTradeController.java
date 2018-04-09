package com.waben.stock.applayer.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.PromotionStockOptionTradeBusiness;
import com.waben.stock.interfaces.dto.organization.PromotionStockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.PromotionStockOptionTradeQuery;
import com.waben.stock.interfaces.service.organization.PromotionStockOptionTradeInterface;

import io.swagger.annotations.Api;

/**
 * 推广渠道产生的期权交易 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/promotionStockOptionTrade")
@Api(description = "推广渠道产生的期权交易")
public class PromotionStockOptionTradeController implements PromotionStockOptionTradeInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public PromotionStockOptionTradeBusiness business;

	@RequestMapping(value = "/adminPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<PageInfo<PromotionStockOptionTradeDto>> adminPage(@RequestBody PromotionStockOptionTradeQuery query) {
		return new Response<>(business.adminPage(query));
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void export(@RequestBody PromotionStockOptionTradeQuery query) {
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		// PageInfo<PromotionStockOptionTradeDto> result = business.adminPage(query);
	}
	
}
