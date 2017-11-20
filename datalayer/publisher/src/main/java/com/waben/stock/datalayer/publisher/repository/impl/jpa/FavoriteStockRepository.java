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

	FavoriteStock findByPublisherSerialCodeAndStockId(String serialCode, Long stockId);

	@Query("select max(sort) from FavoriteStock")
	Integer maxSort();

	List<FavoriteStock> findByPublisherSerialCode(String serialCode, Sort sort);

}
