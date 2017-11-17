package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;

/**
 * 股票 公共接口
 * 
 * @author luomengan
 *
 */
public interface StockInterface {

	@RequestMapping(value = "/selectStock", method = RequestMethod.GET)
	List<StockDto> selectStock(String keyword);

	@RequestMapping(value = "/getStockRecommendList", method = RequestMethod.GET)
	List<StockRecommendDto> getStockRecommendList();
}
