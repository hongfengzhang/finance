package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesVariety;
import com.waben.stock.datalayer.futures.repository.FuturesVarietyDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesVarietyRepository;

@Repository
public class FuturesVarietyDaoImpl implements FuturesVarietyDao {

	@Autowired
	private FuturesVarietyRepository repository;

	@Override
	public FuturesVariety create(FuturesVariety t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesVariety update(FuturesVariety t) {
		return repository.save(t);
	}

	@Override
	public FuturesVariety retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesVariety> page(int page, int limit) {
		return null;
	}

	@Override
	public Page<FuturesVariety> page(Specification<FuturesVariety> specification, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FuturesVariety> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
