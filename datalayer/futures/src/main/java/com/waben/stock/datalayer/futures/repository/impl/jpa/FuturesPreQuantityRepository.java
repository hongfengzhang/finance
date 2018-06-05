package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesPreQuantity;

public interface FuturesPreQuantityRepository extends CustomJpaRepository<FuturesPreQuantity, Long> {

	List<FuturesPreQuantity> findByContractId(Long contractId);
}
