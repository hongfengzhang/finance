package com.waben.stock.datalayer.stockcontent.repository;

import java.util.List;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;

/**
 * 股票 Dao
 * 
 * @author luomengan
 *
 */
public interface StockDao extends BaseDao<Stock, Long> {

	List<Stock> selectStock(String keyword);

	List<StockRecommendDto> getStockRecommendList();

}
