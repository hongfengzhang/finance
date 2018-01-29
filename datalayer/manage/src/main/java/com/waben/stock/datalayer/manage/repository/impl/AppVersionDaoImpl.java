package com.waben.stock.datalayer.manage.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.manage.entity.AppVersion;
import com.waben.stock.datalayer.manage.repository.AppVersionDao;
import com.waben.stock.datalayer.manage.repository.impl.jpa.AppVersionRepository;

/**
 * app版本 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class AppVersionDaoImpl implements AppVersionDao {

	@Autowired
	private AppVersionRepository repository;

	@Override
	public AppVersion create(AppVersion t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public AppVersion update(AppVersion t) {
		return repository.save(t);
	}

	@Override
	public AppVersion retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<AppVersion> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<AppVersion> page(Specification<AppVersion> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<AppVersion> list() {
		return repository.findAll();
	}

}
