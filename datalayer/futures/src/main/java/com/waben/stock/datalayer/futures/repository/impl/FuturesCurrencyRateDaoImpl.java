package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.repository.FuturesCurrencyRateDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesCurrencyRateRepository;

/**
 * 期货货币汇率 Impl
 * 
 * @author sunl
 *
 */
@Repository
public class FuturesCurrencyRateDaoImpl implements FuturesCurrencyRateDao {

	@Autowired
	private FuturesCurrencyRateRepository repository;

	@Override
	public FuturesCurrencyRate create(FuturesCurrencyRate t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesCurrencyRate update(FuturesCurrencyRate t) {
		return repository.save(t);
	}

	@Override
	public FuturesCurrencyRate retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesCurrencyRate> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FuturesCurrencyRate> page(Specification<FuturesCurrencyRate> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesCurrencyRate> list() {
		return repository.findAll();
	}

}
