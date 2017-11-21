package com.waben.stock.datalayer.stockcontent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.repository.StockDao;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;

/**
 * 股票 Service
 * 
 * @author luomengan
 *
 */
@Service
public class StockService {

	@Autowired
	private StockDao stockDao;

	/**
	 * 查询股票，匹配股票名称/代码/简拼
	 * 
	 * @param keyword
	 *            关键字
	 * @param limit
	 *            取多少条
	 * @return 股票结果列表
	 */
	public List<StockDto> selectStock(String keyword, Integer limit) {
		List<StockDto> result = new ArrayList<>();
		List<Stock> entityList = stockDao.selectStock(keyword, limit);
		for (Stock entity : entityList) {
			result.add(entity.copy());
		}
		return result;
	}

	/**
	 * 获取股票推荐列表
	 * 
	 * @return 股票推荐列表
	 */
	public List<StockRecommendDto> getStockRecommendList() {
		return stockDao.getStockRecommendList();
	}

	/**
	 * 根据ID获取股票
	 * 
	 * @param id
	 *            股票ID
	 * @return 股票
	 */
	public StockDto findById(Long id) {
		Stock stock = stockDao.retrieve(id);
		return stock != null ? stock.copy() : null;
	}

}
