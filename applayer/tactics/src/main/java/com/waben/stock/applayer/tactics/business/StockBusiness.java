package com.waben.stock.applayer.tactics.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.service.StockService;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;

/**
 * 股票 Business
 * 
 * @author luomengan
 *
 */
@Service
public class StockBusiness {

	@Autowired
	private StockService stockService;

	public PageInfo<StockDto> pages(StockQuery stockQuery) {
		Response<PageInfo<StockDto>> response = stockService.pagesByQuery(stockQuery);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockDto findById(Long stockId) {
		Response<StockDto> response = stockService.fetchById(stockId);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
