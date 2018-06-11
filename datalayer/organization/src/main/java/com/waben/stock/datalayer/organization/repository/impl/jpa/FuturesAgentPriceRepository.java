package com.waben.stock.datalayer.organization.repository.impl.jpa;

import com.waben.stock.datalayer.organization.entity.FuturesAgentPrice;

public interface FuturesAgentPriceRepository extends CustomJpaRepository<FuturesAgentPrice, Long> {

	FuturesAgentPrice findByContractIdAndOrgId(Long contractId, Long orgId);
}
