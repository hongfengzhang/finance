package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.repository.FuturesContractTermDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesContractTermRepository;

/**
 * 期货合约期限 Impl
 * 
 * @author sunl
 *
 */
@Repository
public class FuturesContractTermDaoImpl implements FuturesContractTermDao {

	@Autowired
	private FuturesContractTermRepository repository;

	@Override
	public FuturesContractTerm create(FuturesContractTerm t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesContractTerm update(FuturesContractTerm t) {
		return repository.save(t);
	}

	@Override
	public FuturesContractTerm retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesContractTerm> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FuturesContractTerm> page(Specification<FuturesContractTerm> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesContractTerm> list() {
		return repository.findAll();
	}

	@Override
	public List<FuturesContractTerm> retrieveByContractAndCurrent(FuturesContract contract, boolean current) {
		return repository.findByContractAndCurrent(contract, current);
	}

	@Override
	public List<FuturesContractTerm> findByListContractId(Long contractId) {
		return repository.findByContractId(contractId);
	}

}
