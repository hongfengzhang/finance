package com.waben.stock.datalayer.futures.repository;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesContract;

/**
 * 期货合约 Dao
 * 
 * @author sl
 *
 */
public interface FuturesContractDao extends BaseDao<FuturesContract, Long> {
	
	int isCurrent(Boolean current,Long id);
	
	List<FuturesContract> findByCommodityId(Long commodityId);
}
