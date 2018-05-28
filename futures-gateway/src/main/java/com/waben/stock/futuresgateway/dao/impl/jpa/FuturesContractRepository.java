package com.waben.stock.futuresgateway.dao.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.waben.stock.futuresgateway.entity.FuturesContract;

/**
 * 期货合约 Repository
 * 
 * @author luomengan
 *
 */
public interface FuturesContractRepository extends Repository<FuturesContract, Long> {

	FuturesContract save(FuturesContract futuresContract);

	void delete(Long id);

	Page<FuturesContract> findAll(Pageable pageable);
	
	List<FuturesContract> findAll();

	FuturesContract findById(Long id);

	FuturesContract findBySymbolIgnoreCase(String symbol);
	
}
