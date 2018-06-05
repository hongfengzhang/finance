package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesGlobalConfig;
import com.waben.stock.datalayer.futures.repository.FuturesGlobalConfigDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesGlobalConfigRepository;
import com.waben.stock.interfaces.pojo.query.PageInfo;
/**
 * 风控警戒线 Impl
 * @author pzl
 *
 */

@Repository
public class FuturesGlobalConfigDaoImpl implements FuturesGlobalConfigDao {
	
	@Autowired
	private FuturesGlobalConfigRepository repository;

	@Override
	public FuturesGlobalConfig create(FuturesGlobalConfig t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FuturesGlobalConfig update(FuturesGlobalConfig t) {
		return repository.save(t);
	}

	@Override
	public FuturesGlobalConfig retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FuturesGlobalConfig> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FuturesGlobalConfig> page(Specification<FuturesGlobalConfig> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesGlobalConfig> list() {
		return repository.findAll();
	}

}
