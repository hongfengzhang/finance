package com.waben.stock.applayer.tactics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.FavoriteStockService;
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

	@Autowired
	private FavoriteStockService favoriteStockService;

	@GetMapping("/selectStock")
	@ApiOperation(value = "查询股票，匹配股票名称/代码/简拼")
	public Response<List<StockDto>> selectStock(String keyword) {
		Response<List<StockDto>> result = stockService.selectStock(keyword, 20);
		if ("200".equals(result.getCode()) && result.getResult() != null && result.getResult().size() > 0) {
			String serialCode = SecurityUtil.getSerialCode();
			if (serialCode != null) {
				Response<List<Long>> stockIds = favoriteStockService.findStockIdByPublisherSerialCode(serialCode);
				if (stockIds.getResult() != null && stockIds.getResult().size() > 0) {
					for (StockDto stockDto : result.getResult()) {
						if (stockIds.getResult().contains(stockDto.getId())) {
							stockDto.setFavorite(true);
						}
					}
				}
			}
		}
		return result;
	}

	@GetMapping("/getStockRecommendList")
	@ApiOperation(value = "获取股票推荐列表")
	public Response<List<StockRecommendDto>> getStockRecommendList() {
		return stockService.getStockRecommendList();
	}

}
