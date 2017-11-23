package com.waben.stock.datalayer.buyrecord.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.buyrecord.entity.DeferredCharges;
import com.waben.stock.datalayer.buyrecord.repository.DeferredChargesDao;
import com.waben.stock.datalayer.buyrecord.repository.impl.jpa.DeferredChargesRepository;

/**
 * 点买递延费 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class DeferredChargesDaoImpl implements DeferredChargesDao {

	@Autowired
	private DeferredChargesRepository repository;

	@Override
	public DeferredCharges create(DeferredCharges t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public DeferredCharges update(DeferredCharges t) {
		return repository.save(t);
	}

	@Override
	public DeferredCharges retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<DeferredCharges> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<DeferredCharges> page(Specification<DeferredCharges> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<DeferredCharges> list() {
		return repository.findAll();
	}

}
