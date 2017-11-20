package com.waben.stock.datalayer.publisher.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public BindCard findByPublisherSerialCodeAndBankCard(String serialCode, String bankCard) {
		return repository.findByPublisherSerialCodeAndBankCard(serialCode, bankCard);
	}

	@Override
	public List<BindCard> findByPublisherSerialCode(String serialCode) {
		return repository.findByPublisherSerialCode(serialCode);
	}

}
