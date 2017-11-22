package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 股票 公共接口
 * 
 * @author luomengan
 *
 */
public interface StockInterface {

	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	Response<StockDto> findById(@RequestParam("id") Long id);

	@RequestMapping(value = "/selectStock", method = RequestMethod.GET)
	Response<List<StockDto>> selectStock(@RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "limit") Integer limit);

	@RequestMapping(value = "/getStockRecommendList", method = RequestMethod.GET)
	Response<List<StockRecommendDto>> getStockRecommendList();

}
