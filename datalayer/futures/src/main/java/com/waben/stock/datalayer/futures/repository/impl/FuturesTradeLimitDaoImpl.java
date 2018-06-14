package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesTradeLimit;
import com.waben.stock.datalayer.futures.repository.FuturesTradeLimitDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesTradeLimitRepository;

@Repository
public class FuturesTradeLimitDaoImpl implements FuturesTradeLimitDao {

	@Autowired
	private FuturesTradeLimitRepository repository;
	
	@Override
	public FuturesTradeLimit create(FuturesTradeLimit t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesTradeLimit update(FuturesTradeLimit t) {
		return repository.save(t);
	}

	@Override
	public FuturesTradeLimit retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesTradeLimit> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FuturesTradeLimit> page(Specification<FuturesTradeLimit> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesTradeLimit> list() {
		return repository.findAll();
	}

	@Override
	public List<FuturesTradeLimit> findByContractId(Long contractId) {
		return repository.findByContractId(contractId);
	}

	@Override
	public void deleteByContractId(Long contractId) {
		repository.deleteBycontractId(contractId);
	}

}
