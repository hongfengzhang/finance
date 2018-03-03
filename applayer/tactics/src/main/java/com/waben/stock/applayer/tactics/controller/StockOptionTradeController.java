package com.waben.stock.applayer.tactics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.StockOptionCycleBusiness;
import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期权交易 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/stockoptiontrade")
@Api(description = "期权交易")
public class StockOptionTradeController {

	@Autowired
	private StockOptionCycleBusiness cycleBusiness;

	@GetMapping("/cyclelists")
	@ApiOperation(value = "期权周期列表")
	public Response<List<StockOptionCycleDto>> lists() {
		return new Response<>(cycleBusiness.lists());
	}

}
