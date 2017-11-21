package com.waben.stock.datalayer.stockcontent.repository;

import com.waben.stock.datalayer.stockcontent.entity.Stock;

import java.util.List;

/**
 * 股票 Dao
 *
 * @author luomengan
 */
public interface StockDao extends BaseDao<Stock, Long> {

<<<<<<< HEAD
=======
	List<Stock> selectStock(String keyword, Integer limit);

	List<StockRecommendDto> getStockRecommendList();

>>>>>>> 5091503fca7ec8bf69793ce6aebaded483393d07
}
