package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;

/**
 * 期货货币汇率 Repository
 * 
 * @author sunl
 *
 */
public interface FuturesCurrencyRateRepository extends CustomJpaRepository<FuturesCurrencyRate, Long> {

	List<FuturesCurrencyRate> findByCurrency(String currency);

	@Query(value = "select * from futures_currency_rate where currency_name=?1", nativeQuery=true)
	List<FuturesCurrencyRate> findByCurrencyName(String currencyName);
}
