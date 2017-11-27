package com.waben.stock.datalayer.buyrecord.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.repository.BuyRecordDao;

/**
 * 点买记录 Service
 * 
 * @author luomengan
 *
 */
@Service
public class BuyRecordService {

	@Autowired
	private BuyRecordDao buyRecordDao;

	public BuyRecord save(BuyRecord buyRecord) {
		return buyRecordDao.create(buyRecord);
	}

}
