package com.waben.stock.datalayer.stockoption.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.stockoption.entity.StockOptionOrg;
import com.waben.stock.datalayer.stockoption.repository.StockOptionOrgDao;
import com.waben.stock.datalayer.stockoption.repository.impl.jpa.StockOptionOrgRepository;

/**
 * 期权第三方机构 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class StockOptionOrgDaoImpl implements StockOptionOrgDao {

	@Autowired
	private StockOptionOrgRepository repository;

	@Override
	public StockOptionOrg create(StockOptionOrg t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public StockOptionOrg update(StockOptionOrg t) {
		return repository.save(t);
	}

	@Override
	public StockOptionOrg retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<StockOptionOrg> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<StockOptionOrg> page(Specification<StockOptionOrg> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<StockOptionOrg> list() {
		return repository.findAll();
	}

}
