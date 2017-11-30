package com.waben.stock.datalayer.publisher.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.publisher.entity.BindCard;
import com.waben.stock.datalayer.publisher.repository.BindCardDao;
import com.waben.stock.datalayer.publisher.repository.impl.jpa.BindCardRepository;

/**
 * 绑卡 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class BindCardDaoImpl implements BindCardDao {

	@Autowired
	private BindCardRepository repository;

	@Override
	public BindCard create(BindCard t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public BindCard update(BindCard t) {
		return repository.save(t);
	}

	@Override
	public BindCard retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<BindCard> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<BindCard> list() {
		return repository.findAll();
	}

	@Override
	public Page<BindCard> page(Specification<BindCard> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public BindCard retriveByPublisherIdAndBankCard(Long publisherId, String bankCard) {
		return repository.findByPublisherIdAndBankCard(publisherId, bankCard);
	}

	@Override
	public List<BindCard> listByPublisherId(Long publisherId) {
		return repository.findByPublisherId(publisherId);
	}

}
