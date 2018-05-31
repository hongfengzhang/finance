package com.waben.stock.datalayer.futures.repository.impl.jpa;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;

/**
 * 期货货币汇率 Repository
 * 
 * @author sunl
 *
 */
public interface FuturesCurrencyRateRepository extends CustomJpaRepository<FuturesCurrencyRate, Long> {

	FuturesCurrencyRate findByCurrency(String currency);
}
