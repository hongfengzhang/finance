package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.service.StockService;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockExponentDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author yuyidi 2017-11-22 10:08:33
 * @class com.waben.stock.datalayer.stockcontent.controller.StockController
 * @description
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockInterface {

	@Autowired
	private StockService stockService;

	@Override
	public Response<PageInfo<StockDto>> pagesByQuery(@RequestBody StockQuery staffQuery) {
		Page<Stock> stocks = stockService.stocks(staffQuery);
		PageInfo<StockDto> result = new PageInfo<>(stocks, StockDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<StockDto> fetchById(@PathVariable Long id) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(StockDto.class, stockService.findById(id), false));
	}

	@Override
	public Response<StockDto> fetchWithExponentByCode(@PathVariable String code) {
		Stock stock = stockService.findByCode(code);
		StockDto result = CopyBeanUtils.copyBeanProperties(StockDto.class, stock, false);
		if (stock.getExponent() != null) {
			result.setStockExponentDto(
					CopyBeanUtils.copyBeanProperties(StockExponentDto.class, stock.getExponent(), false));
		}
		return new Response<>(result);
	}

}
