package com.waben.stock.datalayer.stockcontent.repository;

import java.util.List;

import com.waben.stock.datalayer.stockcontent.entity.Stock;

/**
 * 股票 Dao
 *
 * @author luomengan
 */
public interface StockDao extends BaseDao<Stock, Long> {

	List<Stock> selectStock(String keyword, Integer limit);

}
