package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

	@Query("select f from FuturesContractTerm f where f.current= 1 and f.contract.id = ?1")
	List<FuturesContractTerm> findByContractId(Long contractId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from f_futures_contract_term where contract_id = ?1", nativeQuery=true)
	int deleteBycontractId(Long contractId);
}
