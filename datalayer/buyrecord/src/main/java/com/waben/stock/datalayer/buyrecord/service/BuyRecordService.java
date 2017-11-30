package com.waben.stock.datalayer.buyrecord.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.repository.BuyRecordDao;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.BuyRecordStatus;
import com.waben.stock.interfaces.util.CopyBeanUtils;

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

	public BuyRecordDto addBuyRecord(BuyRecordDto buyRecordDto) {
		BuyRecord entity = CopyBeanUtils.copyBeanProperties(BuyRecord.class, buyRecordDto, false);
		entity.setStatus(BuyRecordStatus.Issue);
		entity.setCreateTime(new Date());
		buyRecordDao.create(entity);
		return CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, entity, false);
	}

}