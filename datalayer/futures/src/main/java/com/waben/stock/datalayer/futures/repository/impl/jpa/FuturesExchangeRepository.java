package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesCommodity;
import com.waben.stock.datalayer.futures.entity.FuturesExchange;

/**
 * 期货交易所 Reopsitory
 * 
 * @author sunl
 *
 */
public interface FuturesExchangeRepository extends CustomJpaRepository<FuturesExchange, Long> {

	@Query("select f from FuturesCommodity f where f.enable=1 and f.exchange.id=?1")
	List<FuturesCommodity> findByExchangId(Long exchangeId);

}
