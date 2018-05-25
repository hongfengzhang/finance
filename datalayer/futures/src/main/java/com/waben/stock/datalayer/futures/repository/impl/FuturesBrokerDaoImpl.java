package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesBroker;
import com.waben.stock.datalayer.futures.repository.FuturesBrokerDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesBrokerRepository;

/**
 * 期货券商 Impl
 * 
 * @author sunl
 *
 */
@Repository
public class FuturesBrokerDaoImpl implements FuturesBrokerDao {

	@Autowired
	private FuturesBrokerRepository repository;

	@Override
	public FuturesBroker create(FuturesBroker t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesBroker update(FuturesBroker t) {
		return repository.save(t);
	}

	@Override
	public FuturesBroker retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesBroker> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FuturesBroker> page(Specification<FuturesBroker> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesBroker> list() {
		return repository.findAll();
	}

}
