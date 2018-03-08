package com.waben.stock.datalayer.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.promotion.pojo.bean.PromotionStockOptionTradeBean;
import com.waben.stock.datalayer.promotion.pojo.query.PromotionStockOptionTradeQuery;
import com.waben.stock.datalayer.promotion.service.PromotionStockOptionTradeService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 推广渠道产生的期权交易 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/promotionStockOptionTrade")
@Api(description = "推广渠道产生的期权交易")
public class PromotionStockOptionTradeController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public PromotionStockOptionTradeService service;

	@PostMapping("/adminPage")
	@ApiOperation(value = "获取推广渠道产生的期权交易分页数据(后台管理)")
	public Response<Page<PromotionStockOptionTradeBean>> adminPage(@RequestBody PromotionStockOptionTradeQuery query) {
		return new Response<>((Page<PromotionStockOptionTradeBean>) service.pagesByQuery(query));
	}

}
