package com.waben.stock.datalayer.stockcontent.repository.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.waben.stock.datalayer.stockcontent.entity.StockRecommend;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;

/**
 * 股票推荐 Jpa
 * 
 * @author luomengan
 *
 */
public interface StockRecommendRepository extends CustomJpaRepository<StockRecommend, Long> {

	List<StockRecommendDto> findAll(Sort sort);

}
