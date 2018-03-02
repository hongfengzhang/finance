package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.OrganizationCategory;
import com.waben.stock.datalayer.promotion.repository.OrganizationCategoryDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.OrganizationCategoryRepository;

/**
 * 机构类别 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class OrganizationCategoryDaoImpl implements OrganizationCategoryDao {

	@Autowired
	private OrganizationCategoryRepository repository;

	@Override
	public OrganizationCategory create(OrganizationCategory t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public OrganizationCategory update(OrganizationCategory t) {
		return repository.save(t);
	}

	@Override
	public OrganizationCategory retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<OrganizationCategory> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<OrganizationCategory> page(Specification<OrganizationCategory> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<OrganizationCategory> list() {
		return repository.findAll();
	}

}
