package com.waben.stock.datalayer.publisher.repository.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.publisher.entity.FavoriteStock;

/**
 * 收藏股票 Jpa
 * 
 * @author luomengan
 *
 */
public interface FavoriteStockRepository extends CustomJpaRepository<FavoriteStock, Long> {

	FavoriteStock findByPublisherIdAndStockId(Long publisherId, Long stockId);

	@Query("select max(sort) from FavoriteStock where publisherId=?1")
	Integer retriveMaxSort(Long publisherId);

	List<FavoriteStock> findByPublisherId(Long publisherId, Sort sort);

	@Query("select stockId from FavoriteStock where publisherId=?1")
	List<Long> findStockIdByPublisherId(Long publisherId);

	List<FavoriteStock> findByPublisherIdAndStockIdNotIn(Long publisherId, Long[] stockIds);

	void deleteByPublisherIdAndStockId(Long publisherId, Long stockId);

}
