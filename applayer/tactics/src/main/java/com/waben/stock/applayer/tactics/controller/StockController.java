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
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;

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
		Response<List<StockDto>> result = new Response<>();

		StockQuery stockQuery = new StockQuery();
		stockQuery.setKeyword(keyword);
		stockQuery.setPage(0);
		stockQuery.setSize(20);
		Response<PageInfo<StockDto>> pages = stockService.pagesByQuery(stockQuery);
		if ("200".equals(pages.getCode()) && pages.getResult() != null && pages.getResult().getContent().size() > 0) {
			String serialCode = SecurityUtil.getSerialCode();
			if (serialCode != null) {
				Response<List<Long>> stockIds = favoriteStockService.listsStockId(SecurityUtil.getUserId());
				if (stockIds.getResult() != null && stockIds.getResult().size() > 0) {
					for (StockDto stockDto : pages.getResult().getContent()) {
						if (stockIds.getResult().contains(stockDto.getId())) {
							// stockDto.setFavorite(true);
						}
					}
				}
			}
			result.setResult(pages.getResult().getContent());
		} else {
			result.setCode(pages.getCode());
			result.setMessage(pages.getMessage());
		}
		return result;
	}

	@GetMapping("/getStockRecommendList")
	@ApiOperation(value = "获取股票推荐列表")
	public Response<List<StockRecommendDto>> getStockRecommendList() {
		// return stockService.getStockRecommendList();
		return new Response<>();
	}

}
