package com.waben.stock.datalayer.futures.repository;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;

/**
 * 期货货币汇率 Dao
 * 
 * @author sunl
 *
 */
public interface FuturesCurrencyRateDao extends BaseDao<FuturesCurrencyRate, Long> {

	List<FuturesCurrencyRate> findAll();

	FuturesCurrencyRate findByCurrency(String currency);
}
