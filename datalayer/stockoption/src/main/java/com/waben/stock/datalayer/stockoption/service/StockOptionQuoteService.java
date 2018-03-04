package com.waben.stock.datalayer.stockoption.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.stockoption.entity.StockOptionQuote;
import com.waben.stock.datalayer.stockoption.repository.StockOptionQuoteDao;

/**
 * 期权报价 Service
 * 
 * @author luomengan
 *
 */
@Service
public class StockOptionQuoteService {

	@Autowired
	private StockOptionQuoteDao quoteDao;

	public StockOptionQuote quote(String stockCode, Integer cycle) {
		return quoteDao.retrieveByStockCodeAndCycle(stockCode, cycle);
	}

}
