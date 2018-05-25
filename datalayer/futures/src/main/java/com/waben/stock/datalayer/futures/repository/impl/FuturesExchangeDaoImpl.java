package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesExchange;
import com.waben.stock.datalayer.futures.repository.FuturesExchangeDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesExchangeRepository;

/**
 * 期货交易所 Impl
 * 
 * @author sunl
 *
 */
@Repository
public class FuturesExchangeDaoImpl implements FuturesExchangeDao {

	@Autowired
	private FuturesExchangeRepository repository;

	@Override
	public FuturesExchange create(FuturesExchange t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesExchange update(FuturesExchange t) {
		return repository.save(t);
	}

	@Override
	public FuturesExchange retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesExchange> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FuturesExchange> page(Specification<FuturesExchange> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesExchange> list() {
		return repository.findAll();
	}

}
