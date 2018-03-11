package com.waben.stock.datalayer.organization.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.repository.OrganizationDao;
import com.waben.stock.datalayer.organization.repository.impl.jpa.OrganizationRepository;

/**
 * 机构 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {

	@Autowired
	private OrganizationRepository repository;

	@Override
	public Organization create(Organization t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Organization update(Organization t) {
		return repository.save(t);
	}

	@Override
	public Organization retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Organization> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<Organization> page(Specification<Organization> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<Organization> list() {
		return repository.findAll();
	}

	@Override
	public List<Organization> listByParent(Organization parent) {
		List<Organization> list = repository.findByParent(parent); 
		return list;
	}

	@Override
	public List<Organization> listByParentOrderByCodeDesc(Organization parent) {
		return repository.findByParent(parent, new Sort(new Sort.Order(Direction.DESC, "code")));
	}

	@Override
	public Organization retrieveByCode(String orgCode) {
		return repository.findByCode(orgCode);
	}

}
