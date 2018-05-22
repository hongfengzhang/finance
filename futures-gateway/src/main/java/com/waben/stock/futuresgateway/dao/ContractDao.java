package com.waben.stock.futuresgateway.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.futuresgateway.entity.Contract;

/**
 * 绑卡 Dao
 * 
 * @author luomengan
 *
 */
public interface ContractDao {

	public Contract createContract(Contract bindCard);

	public void deleteContractById(Long id);

	public Contract updateContract(Contract bindCard);

	public Contract retrieveContractById(Long id);

	public Page<Contract> pageContract(int page, int limit);
	
	public List<Contract> listContract();

}
