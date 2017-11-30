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

	FavoriteStock retrive(Long publisherId, Long stockId);

	Integer retriveMaxSort(Long publisherId);

	List<FavoriteStock> list(Long publisherId);

	List<FavoriteStock> listByStockIdNotIn(Long publisherId, Long[] stockIds);

	void delete(Long publisherId, Long[] stockIds);

	List<Long> listStockId(Long publisherId);

}
