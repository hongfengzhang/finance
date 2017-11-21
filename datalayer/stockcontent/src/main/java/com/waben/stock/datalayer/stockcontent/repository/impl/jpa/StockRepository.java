package com.waben.stock.datalayer.stockcontent.repository.impl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.stockcontent.entity.Stock;

/**
 * 股票 Jpa
 * 
 * @author luomengan
 *
 */
public interface StockRepository extends CustomJpaRepository<Stock, Long> {

<<<<<<< HEAD
//	List<Stock> findByNameLikeOrCodeLikeOrPinyinAbbrLike(String name, String code, String pinyinAbbr);
=======
	@Query(value = "select t.* from stock t where t.name like ?1 or t.code like ?2 or t.pinyin_abbr like ?3 limit 0, ?4", nativeQuery = true)
	List<Stock> findByNameLikeOrCodeLikeOrPinyinAbbrLike(String name, String code, String pinyinAbbr, Integer limit);
>>>>>>> 5091503fca7ec8bf69793ce6aebaded483393d07

}
