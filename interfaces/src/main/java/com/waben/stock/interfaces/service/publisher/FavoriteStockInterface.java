package com.waben.stock.interfaces.service.publisher;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.pojo.Response;

public interface FavoriteStockInterface {

	@RequestMapping(value = "/addFavoriteStock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<FavoriteStockDto> addFavoriteStock(@RequestBody FavoriteStockDto favoriteStockDto);

	@RequestMapping(value = "/removeFavoriteStock", method = RequestMethod.POST)
	Response<String> removeFavoriteStock(@RequestParam(name = "serialCode") String serialCode,
			@RequestParam(name = "stockIds") String stockIds);

	@RequestMapping(value = "/topFavoriteStock", method = RequestMethod.POST)
	Response<String> topFavoriteStock(@RequestParam(name = "serialCode") String serialCode,
			@RequestParam(name = "stockIds") String stockIds);

	@RequestMapping(value = "/favoriteStockList", method = RequestMethod.GET)
	Response<List<FavoriteStockDto>> favoriteStockList(@RequestParam(name = "serialCode") String serialCode);
	
	@RequestMapping(value = "/findStockIdByPublisherSerialCode", method = RequestMethod.GET)
	Response<List<Long>> findStockIdByPublisherSerialCode(@RequestParam(name = "serialCode") String serialCode);

}