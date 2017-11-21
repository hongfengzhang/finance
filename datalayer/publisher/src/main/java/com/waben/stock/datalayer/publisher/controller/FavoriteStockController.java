package com.waben.stock.datalayer.publisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.FavoriteStockService;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.FavoriteStockInterface;

/**
 * 收藏股票 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/favoriteStock")
public class FavoriteStockController implements FavoriteStockInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FavoriteStockService favoriteStockService;

	@Override
	public Response<FavoriteStockDto> addFavoriteStock(@RequestBody FavoriteStockDto favoriteStockDto) {
		return new Response<>(favoriteStockService.addFavoriteStock(favoriteStockDto.getPublisherSerialCode(),
				favoriteStockDto.getStockId(), favoriteStockDto.getName(), favoriteStockDto.getCode(),
				favoriteStockDto.getPinyinAbbr()));
	}

	@Override
	public Response<String> removeFavoriteStock(String serialCode, String stockIds) {
		String[] stockIdStrArr = stockIds.split("-");
		Long[] stockIdArr = new Long[stockIdStrArr.length];
		for (int i = 0; i < stockIdStrArr.length; i++) {
			stockIdArr[i] = Long.parseLong(stockIdStrArr[i]);
		}
		favoriteStockService.removeFavoriteStock(serialCode, stockIdArr);
		return new Response<>("successful");
	}

	@Override
	public Response<String> topFavoriteStock(String serialCode, String stockIds) {
		String[] stockIdStrArr = stockIds.split("-");
		Long[] stockIdArr = new Long[stockIdStrArr.length];
		for (int i = 0; i < stockIdStrArr.length; i++) {
			stockIdArr[i] = Long.parseLong(stockIdStrArr[i]);
		}
		favoriteStockService.topFavoriteStock(serialCode, stockIdArr);
		return new Response<>("successful");
	}

	@Override
	public Response<List<FavoriteStockDto>> favoriteStockList(String serialCode) {
		return new Response<>(favoriteStockService.favoriteStockList(serialCode));
	}
	
	@Override
	public Response<List<Long>> findStockIdByPublisherSerialCode(String serialCode) {
		return new Response<>(favoriteStockService.findStockIdByPublisherSerialCode(serialCode));
	}
	
}
