package com.waben.stock.futuresgateway.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.futuresgateway.entity.FuturesContract;

/**
 * 期货合约 Dao
 * 
 * @author luomengan
 *
 */
public interface FuturesContractDao {

	public FuturesContract createContract(FuturesContract futuresContract);

	public void deleteContractById(Long id);

	public FuturesContract updateContract(FuturesContract futuresContract);

	public FuturesContract retrieveContractById(Long id);

	public Page<FuturesContract> pageContract(int page, int limit);
	
	public List<FuturesContract> listContract();

	public FuturesContract retrieveContractBySymbol(String symbol);

}
