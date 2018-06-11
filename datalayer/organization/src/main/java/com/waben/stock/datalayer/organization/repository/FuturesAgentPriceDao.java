package com.waben.stock.datalayer.organization.repository;

import com.waben.stock.datalayer.organization.entity.FuturesAgentPrice;

public interface FuturesAgentPriceDao extends BaseDao<FuturesAgentPrice, Long> {

	FuturesAgentPrice findByContractIdAndOrgId(Long contractId, Long orgId);
}
