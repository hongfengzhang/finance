package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;

/**
 * 期货货币汇率 Repository
 * 
 * @author sunl
 *
 */
public interface FuturesCurrencyRateRepository extends CustomJpaRepository<FuturesCurrencyRate, Long> {

	List<FuturesCurrencyRate> findByCurrency(String currency);

	List<FuturesCurrencyRate> findByCurrencyName(String currencyName);
}
