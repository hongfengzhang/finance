package com.waben.stock.datalayer.publisher.repository.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.publisher.entity.CapitalFlow;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowDao;
import com.waben.stock.datalayer.publisher.repository.impl.jpa.CapitalFlowRepository;
import com.waben.stock.interfaces.enums.CapitalFlowType;

/**
 * 资金流水 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class CapitalFlowDaoImpl implements CapitalFlowDao {

	@Autowired
	private CapitalFlowRepository repository;

	@Override
	public CapitalFlow create(CapitalFlow t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public CapitalFlow update(CapitalFlow t) {
		return repository.save(t);
	}

	@Override
	public CapitalFlow retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<CapitalFlow> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<CapitalFlow> list() {
		return repository.findAll();
	}

	@Override
	public CapitalFlow addCapitalFlow(Long publisherId, String publisherSerialCode, CapitalFlowType type,
			BigDecimal amount, Date occurrenceTime) {
		CapitalFlow t = new CapitalFlow();
		t.setAmount(amount);
		t.setOccurrenceTime(occurrenceTime);
		t.setPublisherId(publisherId);
		t.setPublisherSerialCode(publisherSerialCode);
		t.setType(type);
		t.setRemark(type.getType());
		return repository.save(t);
	}

}