package com.waben.stock.applayer.tactics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.service.StockService;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.ApiOperation;

/**
 * 股票 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping("/selectStock")
	@ApiOperation(value = "查询股票，匹配股票名称/代码/简拼")
	public Response<List<StockDto>> selectStock(String keyword) {
		return new Response<>(stockService.selectStock(keyword));
	}

	@GetMapping("/getStockRecommendList")
	@ApiOperation(value = "获取股票推荐列表")
	public Response<List<StockRecommendDto>> getStockRecommendList() {
		return new Response<>(stockService.getStockRecommendList());
	}

}
