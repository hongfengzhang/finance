package com.waben.stock.datalayer.stockcontent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.stockcontent.service.StockService;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;
import com.waben.stock.interfaces.service.StockInterface;

/**
 * 股票 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockInterface {

	@Autowired
	private StockService stockService;

	@Override
	public List<StockDto> selectStock(String keyword) {
		return stockService.selectStock(keyword);
	}

	@Override
	public List<StockRecommendDto> getStockRecommendList() {
		return stockService.getStockRecommendList();
	}

}
