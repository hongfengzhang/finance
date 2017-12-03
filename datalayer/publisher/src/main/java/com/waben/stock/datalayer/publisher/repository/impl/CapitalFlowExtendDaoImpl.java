package com.waben.stock.datalayer.publisher.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.publisher.entity.CapitalFlowExtend;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowExtendDao;
import com.waben.stock.datalayer.publisher.repository.impl.jpa.CapitalFlowExtendRepository;

/**
 * 资金流水扩展 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class CapitalFlowExtendDaoImpl implements CapitalFlowExtendDao {

	@Autowired
	private CapitalFlowExtendRepository repository;

	@Override
	public CapitalFlowExtend create(CapitalFlowExtend t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public CapitalFlowExtend update(CapitalFlowExtend t) {
		return repository.save(t);
	}

	@Override
	public CapitalFlowExtend retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<CapitalFlowExtend> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<CapitalFlowExtend> page(Specification<CapitalFlowExtend> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<CapitalFlowExtend> list() {
		return repository.findAll();
	}

}
