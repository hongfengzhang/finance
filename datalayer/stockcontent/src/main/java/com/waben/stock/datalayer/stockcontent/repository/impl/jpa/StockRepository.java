package com.waben.stock.datalayer.stockcontent.repository.impl.jpa;

import java.util.List;

import com.waben.stock.datalayer.stockcontent.entity.Stock;

/**
 * 股票 Jpa
 * 
 * @author luomengan
 *
 */
public interface StockRepository extends CustomJpaRepository<Stock, Long> {

//	List<Stock> findByNameLikeOrCodeLikeOrPinyinAbbrLike(String name, String code, String pinyinAbbr);

}
