package com.waben.stock.applayer.tactics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.FavoriteStockService;
import com.waben.stock.applayer.tactics.service.StockService;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.ApiOperation;

/**
 * 收藏股票 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/favoriteStock")
public class FavoriteStockController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FavoriteStockService favoriteStockService;

	@Autowired
	private StockService stockService;

	@PostMapping("/addFavoriteStock")
	@ApiOperation(value = "收藏股票")
	public Response<FavoriteStockDto> addFavoriteStock(@RequestParam(required = true) Long stockId) {
		FavoriteStockDto favoriteStockDto = new FavoriteStockDto();
		Response<StockDto> stockResp = stockService.findById(stockId);
		favoriteStockDto.setCode(stockResp.getResult().getCode());
		favoriteStockDto.setStockId(stockId);

//	TODO	favoriteStockDto.setPinyinAbbr(stockResp.getResult().getPinyinAbbr());
		favoriteStockDto.setName(stockResp.getResult().getName());
		favoriteStockDto.setPublisherSerialCode(SecurityUtil.getSerialCode());
		return favoriteStockService.addFavoriteStock(favoriteStockDto);
	}

	@PostMapping("/removeFavoriteStock")
	@ApiOperation(value = "删除收藏股票")
	public Response<String> removeFavoriteStock(@RequestParam(required = true) Long[] stockIds) {
		StringBuilder stockIdsStr = new StringBuilder();
		for (int i = 0; i < stockIds.length; i++) {
			stockIdsStr.append(stockIds[i]);
			if (i != stockIds.length - 1) {
				stockIdsStr.append("-");
			}
		}
		return favoriteStockService.removeFavoriteStock(SecurityUtil.getSerialCode(), stockIdsStr.toString());
	}

	@PostMapping("/topFavoriteStock")
	@ApiOperation(value = "置顶收藏股票")
	public Response<String> topFavoriteStock(@RequestParam(required = true) Long[] stockIds) {
		StringBuilder stockIdsStr = new StringBuilder();
		for (int i = 0; i < stockIds.length; i++) {
			stockIdsStr.append(stockIds[i]);
			if (i != stockIds.length - 1) {
				stockIdsStr.append("-");
			}
		}
		return favoriteStockService.topFavoriteStock(SecurityUtil.getSerialCode(), stockIdsStr.toString());
	}

	@GetMapping("/favoriteStockList")
	@ApiOperation(value = "获取收藏股票")
	public Response<List<FavoriteStockDto>> favoriteStockList() {
		return favoriteStockService.favoriteStockList(SecurityUtil.getSerialCode());
	}

}
