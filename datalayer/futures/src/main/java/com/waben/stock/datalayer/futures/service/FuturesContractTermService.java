package com.waben.stock.datalayer.futures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.repository.FuturesContractTermDao;

/**
 * 期货合约期限 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesContractTermService {

	@Autowired
	private FuturesContractTermDao termDao;
	
	public FuturesContractTerm addContractTerm(FuturesContractTerm dto) {
		return termDao.create(dto);
	}

	public FuturesContractTerm modifyContractTerm(FuturesContractTerm dto) {
		return termDao.update(dto);
	}

	public void deleteContractTerm(Long id) {
		termDao.delete(id);
	}

	public FuturesContractTerm getContractCurrentTerm(FuturesContract contract) {
		List<FuturesContractTerm> termList = termDao.retrieveByContractAndCurrent(contract, true);
		if (termList != null && termList.size() > 0) {
			return termList.get(0);
		}
		return null;
	}

}
