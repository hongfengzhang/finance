package com.waben.stock.applayer.promotion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.stockoption.StockOptionCycleBusiness;
import com.waben.stock.applayer.promotion.business.stockoption.StockOptionTradeBusiness;
import com.waben.stock.applayer.promotion.security.SecurityUtil;
import com.waben.stock.interfaces.dto.promotion.stockoption.StockOptionPromotionDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.promotion.stockoption.StockOptionPromotionQuery;

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
	public StockOptionTradeBusiness business;
	
	@Autowired
    private StockOptionCycleBusiness cycleBusiness;

    @GetMapping("/cycles")
    @ApiOperation(value = "获取所有期权周期")
    public Response<List<StockOptionCycleDto>> fetchAll() {
        List<StockOptionCycleDto> response = cycleBusiness.findAll();
        return new Response<>(response);
    }

	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public Response<PageInfo<StockOptionPromotionDto>> promotionPagesByQuery(StockOptionPromotionQuery query) {
		query.setCurrentOrgId(SecurityUtil.getUserDetails().getOrgId());
		return new Response<>(business.promotionPagesByQuery(query));
	}

}
