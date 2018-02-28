package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.Role;
import com.waben.stock.datalayer.promotion.repository.RoleDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.RoleRepository;

/**
 * 角色 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private RoleRepository repository;

	@Override
	public Role create(Role t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Role update(Role t) {
		return repository.save(t);
	}

	@Override
	public Role retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Role> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<Role> page(Specification<Role> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<Role> list() {
		return repository.findAll();
	}

}
