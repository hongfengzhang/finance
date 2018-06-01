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

	@Query(value = "select f from FuturesContractTerm f where f.contract.id=?1 ")
	List<FuturesContractTerm> findByListContractId(@PathVariable("contractId") Long contractId);
}
