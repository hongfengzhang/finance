package com.waben.stock.datalayer.buyrecord.service;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.repository.BuyRecordDao;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.util.SerialCodeGenerator;

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
		buyRecord.setSerialCode(SerialCodeGenerator.generate());
		buyRecord.setState(BuyRecordState.POSTED);
		buyRecord.setCreateTime(new Date());
		return buyRecordDao.create(buyRecord);
	}

	public Page<BuyRecord> pagesByQuery(final BuyRecordQuery buyRecordQuery) {
		Pageable pageable = new PageRequest(buyRecordQuery.getPage(), buyRecordQuery.getSize());
		Page<BuyRecord> pages = buyRecordDao.page(new Specification<BuyRecord>() {
			@Override
			public Predicate toPredicate(Root<BuyRecord> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				if (buyRecordQuery.getStates() != null && buyRecordQuery.getStates().length > 0) {
					criteriaQuery.where(root.get("state").in(buyRecordQuery.getStates()));
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public BuyRecord changeState(BuyRecord record) {
		BuyRecordState current = record.getState();
		BuyRecordState next = BuyRecordState.UNKONWN;
		if (BuyRecordState.POSTED.equals(current)) {
			next = BuyRecordState.BUYLOCK;
		} else if (BuyRecordState.BUYLOCK.equals(current)) {
			next = BuyRecordState.HOLDPOSITION;
		} else if (BuyRecordState.HOLDPOSITION.equals(current)) {
			next = BuyRecordState.SELLLOCK;
		}
		record.setState(next);
		BuyRecord result = buyRecordDao.update(record);
		if (result.getState().equals(next)) {
			logger.info("点买交易状态更新成功,id:{}", result.getSerialCode());
			if (next.equals(BuyRecordState.HOLDPOSITION)) {
				//若点买交易记录为持仓中，向消息队列中添加当前点买交易记录

			}
		}
		return result;
	}

}
