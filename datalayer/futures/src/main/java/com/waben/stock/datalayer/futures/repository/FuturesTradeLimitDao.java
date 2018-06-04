package com.waben.stock.datalayer.futures.repository;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesTradeLimit;

public interface FuturesTradeLimitDao extends BaseDao<FuturesTradeLimit, Long> {

	List<FuturesTradeLimit> findByContractId(Long contractId);
}
