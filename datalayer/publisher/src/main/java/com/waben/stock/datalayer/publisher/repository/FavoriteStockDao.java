package com.waben.stock.datalayer.publisher.repository;

import java.util.List;

import com.waben.stock.datalayer.publisher.entity.FavoriteStock;

/**
 * 收藏股票 Dao
 * 
 * @author luomengan
 *
 */
public interface FavoriteStockDao extends BaseDao<FavoriteStock, Long> {

	FavoriteStock findByPublisherSerialCodeAndStockId(String serialCode, Long stockId);

	Integer maxSort();

	List<FavoriteStock> favoriteStockList(String serialCode);

	List<FavoriteStock> findByStockIdNotIn(Long[] stockIds);

	void deleteBySerialCodeAndStockIdIn(String serialCode, Long[] stockIds);

	List<Long> findStockIdByPublisherSerialCode(String serialCode);

}
