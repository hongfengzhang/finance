package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesExchange;

/**
 * 期货交易所 Reopsitory
 * 
 * @author sunl
 *
 */
public interface FuturesExchangeRepository extends CustomJpaRepository<FuturesExchange, Long> {
	@Query("select f form futuresContract f where f.enable=1 and f.exchange_id=?1")
	List<FuturesContract> findByExchangId(Long exchangeId);

}
