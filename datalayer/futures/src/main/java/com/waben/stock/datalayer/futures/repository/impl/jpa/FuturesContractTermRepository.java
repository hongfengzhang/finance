package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;

/**
 * 期货合约期限 Repostiory
 * 
 * @author sunl
 *
 */
public interface FuturesContractTermRepository extends CustomJpaRepository<FuturesContractTerm, Long> {

	List<FuturesContractTerm> findByContractAndCurrent(FuturesContract contract, boolean current);

}
