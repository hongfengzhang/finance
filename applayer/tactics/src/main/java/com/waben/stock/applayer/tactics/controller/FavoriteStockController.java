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

import com.waben.stock.applayer.tactics.business.FavoriteStockBusiness;
import com.waben.stock.applayer.tactics.business.StockBusiness;
import com.waben.stock.applayer.tactics.dto.publisher.FavoriteStockWithMarketDto;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 收藏股票 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/favoriteStock")
@Api(description = "自选收藏股票")
public class FavoriteStockController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FavoriteStockBusiness favoriteBusiness;

	@Autowired
	private StockBusiness stockBusiness;

	@PostMapping("/addFavoriteStock")
	@ApiOperation(value = "收藏股票")
	public Response<FavoriteStockDto> addFavoriteStock(@RequestParam(required = true) Long stockId) {
		StockDto stockDto = stockBusiness.findById(stockId);
		FavoriteStockDto favorite = new FavoriteStockDto();
		favorite.setCode(stockDto.getCode());
		favorite.setStockId(stockId);
		favorite.setName(stockDto.getName());
		favorite.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(favoriteBusiness.save(favorite));
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
		return new Response<>(favoriteBusiness.remove(SecurityUtil.getUserId(), stockIdsStr.toString()));
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
		return new Response<>(favoriteBusiness.top(SecurityUtil.getUserId(), stockIdsStr.toString()));
	}

	@GetMapping("/favoriteStockList")
	@ApiOperation(value = "获取收藏股票")
	public Response<List<FavoriteStockWithMarketDto>> favoriteStockList() {
		return new Response<>(favoriteBusiness.listsByPublisherId(SecurityUtil.getUserId()));
	}

}
