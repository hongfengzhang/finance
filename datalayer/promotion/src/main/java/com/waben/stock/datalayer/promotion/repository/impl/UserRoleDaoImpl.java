package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.UserRole;
import com.waben.stock.datalayer.promotion.repository.UserRoleDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.UserRoleRepository;

/**
 * 用户角色 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private UserRoleRepository repository;

	@Override
	public UserRole create(UserRole t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public UserRole update(UserRole t) {
		return repository.save(t);
	}

	@Override
	public UserRole retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<UserRole> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<UserRole> page(Specification<UserRole> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<UserRole> list() {
		return repository.findAll();
	}

}
