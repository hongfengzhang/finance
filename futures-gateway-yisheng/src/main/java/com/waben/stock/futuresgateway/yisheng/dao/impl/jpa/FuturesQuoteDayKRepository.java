package com.waben.stock.futuresgateway.yisheng.dao.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuoteDayK;

/**
 * 行情-日K Repository
 * 
 * @author luomengan
 *
 */
public interface FuturesQuoteDayKRepository extends Repository<FuturesQuoteDayK, Long> {

	FuturesQuoteDayK save(FuturesQuoteDayK futuresQuoteDayK);

	void delete(Long id);

	Page<FuturesQuoteDayK> findAll(Pageable pageable);
	
	List<FuturesQuoteDayK> findAll();

	FuturesQuoteDayK findById(Long id);
	
}
