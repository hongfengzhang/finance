package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.BindingCard;
import com.waben.stock.datalayer.promotion.repository.BindingCardDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.BindingCardRepository;

/**
 * 绑卡 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class BindingCardDaoImpl implements BindingCardDao {

	@Autowired
	private BindingCardRepository repository;

	@Override
	public BindingCard create(BindingCard t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public BindingCard update(BindingCard t) {
		return repository.save(t);
	}

	@Override
	public BindingCard retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<BindingCard> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<BindingCard> page(Specification<BindingCard> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<BindingCard> list() {
		return repository.findAll();
	}

}
