package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.PromotionBuyRecord;
import com.waben.stock.datalayer.promotion.repository.PromotionBuyRecordDao;

/**
 * 推广渠道产生的策略 Service
 * 
 * @author luomengan
 *
 */
@Service
public class PromotionBuyRecordService {

	@Autowired
	private PromotionBuyRecordDao promotionBuyRecordDao;

	public PromotionBuyRecord getPromotionBuyRecordInfo(Long id) {
		return promotionBuyRecordDao.retrieve(id);
	}

	@Transactional
	public PromotionBuyRecord addPromotionBuyRecord(PromotionBuyRecord promotionBuyRecord) {
		return promotionBuyRecordDao.create(promotionBuyRecord);
	}

	@Transactional
	public PromotionBuyRecord modifyPromotionBuyRecord(PromotionBuyRecord promotionBuyRecord) {
		return promotionBuyRecordDao.update(promotionBuyRecord);
	}

	@Transactional
	public void deletePromotionBuyRecord(Long id) {
		promotionBuyRecordDao.delete(id);
	}
	
	@Transactional
	public void deletePromotionBuyRecords(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					promotionBuyRecordDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<PromotionBuyRecord> promotionBuyRecords(int page, int limit) {
		return promotionBuyRecordDao.page(page, limit);
	}
	
	public List<PromotionBuyRecord> list() {
		return promotionBuyRecordDao.list();
	}

}
