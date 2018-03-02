package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.RoleMenu;
import com.waben.stock.datalayer.promotion.repository.RoleMenuDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.RoleMenuRepository;

/**
 * 角色菜单 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class RoleMenuDaoImpl implements RoleMenuDao {

	@Autowired
	private RoleMenuRepository repository;

	@Override
	public RoleMenu create(RoleMenu t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public RoleMenu update(RoleMenu t) {
		return repository.save(t);
	}

	@Override
	public RoleMenu retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<RoleMenu> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<RoleMenu> page(Specification<RoleMenu> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<RoleMenu> list() {
		return repository.findAll();
	}

}
