package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesTradeLimit;

public interface FuturesTradeLimitRepository extends CustomJpaRepository<FuturesTradeLimit, Long> {

	@Query(value = "select * from f_futures_contract_limit where contract_id = ?1 and enable = '1'", nativeQuery=true)
	List<FuturesTradeLimit> findByContractId(Long contractId);
}
