package com.waben.stock.datalayer.buyrecord.service;

import com.waben.stock.interfaces.enums.BuyRecordStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuyRecordDao buyRecordDao;

	public BuyRecord save(BuyRecord buyRecord) {
		return buyRecordDao.create(buyRecord);
	}


	public BuyRecord changeState(BuyRecord record) {
		BuyRecordStatus current = record.getStatus();
		BuyRecordStatus next = BuyRecordStatus.UNKONWN;
		if (BuyRecordStatus.POSTED.equals(current)) {
			next = BuyRecordStatus.BUYLOCK;
		} else if (BuyRecordStatus.BUYLOCK.equals(current)) {
			next = BuyRecordStatus.HOLDPOSITION;
		} else if (BuyRecordStatus.HOLDPOSITION.equals(current)) {
			next = BuyRecordStatus.SELLLOCK;
		}
		record.setStatus(next);
		BuyRecord result = buyRecordDao.update(record);
		if (result.getStatus().equals(next)) {
			logger.info("点买交易状态更新成功,id:{}", result.getSerialCode());
			if (next.equals(BuyRecordStatus.HOLDPOSITION)) {
				//若点买交易记录为持仓中，向消息队列中添加当前点买交易记录

			}
		}
		return result;
	}


}
