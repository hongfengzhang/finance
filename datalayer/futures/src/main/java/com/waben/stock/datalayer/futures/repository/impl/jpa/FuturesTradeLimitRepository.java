package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesTradeLimit;

public interface FuturesTradeLimitRepository extends CustomJpaRepository<FuturesTradeLimit, Long> {

	List<FuturesTradeLimit> findByContractId(String contractId);
}
