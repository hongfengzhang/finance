package com.waben.stock.datalayer.futures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;

/**
 * 期货合约期限 Dao
 * 
 * @author sunl
 *
 */
public interface FuturesContractTermDao extends BaseDao<FuturesContractTerm, Long> {

	List<FuturesContractTerm> retrieveByContractAndCurrent(FuturesContract contract, boolean current);

	List<FuturesContractTerm> findByListContractId(Long contractId);
	
	int deleteBycontractId(Long contractId);
}
