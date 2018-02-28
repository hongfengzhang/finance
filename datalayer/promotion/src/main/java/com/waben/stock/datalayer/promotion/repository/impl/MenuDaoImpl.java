package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.Menu;
import com.waben.stock.datalayer.promotion.repository.MenuDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.MenuRepository;

/**
 * 菜单 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class MenuDaoImpl implements MenuDao {

	@Autowired
	private MenuRepository repository;

	@Override
	public Menu create(Menu t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Menu update(Menu t) {
		return repository.save(t);
	}

	@Override
	public Menu retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Menu> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<Menu> page(Specification<Menu> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<Menu> list() {
		return repository.findAll();
	}

}
