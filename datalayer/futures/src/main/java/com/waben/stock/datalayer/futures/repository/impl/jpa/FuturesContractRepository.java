package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;

/**
 * 期货合约 Repository
 * 
 * @author sl
 *
 */
public interface FuturesContractRepository extends CustomJpaRepository<FuturesContract, Long> {

	@Query(value = "select * from f_futures_contract_term f where f.contract_id=?1 ", nativeQuery = true)
	List<FuturesContractTerm> findByListContractId(@PathVariable("contractId") Long contractId);
}
