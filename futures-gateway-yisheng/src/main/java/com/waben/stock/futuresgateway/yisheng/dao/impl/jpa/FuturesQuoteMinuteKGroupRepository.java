package com.waben.stock.futuresgateway.yisheng.dao.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuoteMinuteKGroup;

/**
 * 行情-分钟K组合 Repository
 * 
 * @author luomengan
 *
 */
public interface FuturesQuoteMinuteKGroupRepository extends Repository<FuturesQuoteMinuteKGroup, Long> {

	FuturesQuoteMinuteKGroup save(FuturesQuoteMinuteKGroup futuresQuoteMinuteKGroup);

	void delete(Long id);

	Page<FuturesQuoteMinuteKGroup> findAll(Pageable pageable);
	
	List<FuturesQuoteMinuteKGroup> findAll();

	FuturesQuoteMinuteKGroup findById(Long id);
	
}
