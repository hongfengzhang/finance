package com.waben.stock.datalayer.futures.repository;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;

/**
 * 期货合约 Dao
 * 
 * @author sl
 *
 */
public interface FuturesContractDao extends BaseDao<FuturesContract, Long> {

	
	List<FuturesContractTerm> findByListContractId(Long contractId);
}
