package com.waben.stock.datalayer.manage.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.manage.entity.PutForward;
import com.waben.stock.datalayer.manage.repository.PutForwardDao;
import com.waben.stock.datalayer.manage.repository.impl.jpa.PutForwardRepository;

@Repository
public class PutForwardDaoImpl implements PutForwardDao {

	@Autowired
	private PutForwardRepository repository;
	
	@Override
	public PutForward create(PutForward t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public PutForward update(PutForward t) {
		return repository.save(t);
	}

	@Override
	public PutForward retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<PutForward> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<PutForward> page(Specification<PutForward> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<PutForward> list() {
		return repository.findAll();
	}

}
