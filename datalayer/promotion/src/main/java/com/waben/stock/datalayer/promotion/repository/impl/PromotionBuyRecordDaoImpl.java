package com.waben.stock.datalayer.promotion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.promotion.entity.PromotionBuyRecord;
import com.waben.stock.datalayer.promotion.repository.PromotionBuyRecordDao;
import com.waben.stock.datalayer.promotion.repository.impl.jpa.PromotionBuyRecordRepository;

/**
 * 推广渠道产生的策略 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class PromotionBuyRecordDaoImpl implements PromotionBuyRecordDao {

	@Autowired
	private PromotionBuyRecordRepository repository;

	@Override
	public PromotionBuyRecord create(PromotionBuyRecord t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public PromotionBuyRecord update(PromotionBuyRecord t) {
		return repository.save(t);
	}

	@Override
	public PromotionBuyRecord retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<PromotionBuyRecord> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<PromotionBuyRecord> page(Specification<PromotionBuyRecord> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<PromotionBuyRecord> list() {
		return repository.findAll();
	}

}
