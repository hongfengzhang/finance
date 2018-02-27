package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.Settlement;
import com.waben.stock.datalayer.promotion.repository.SettlementDao;

/**
 * 结算 Service
 * 
 * @author luomengan
 *
 */
@Service
public class SettlementService {

	@Autowired
	private SettlementDao settlementDao;

	public Settlement getSettlementInfo(Long id) {
		return settlementDao.retrieve(id);
	}

	@Transactional
	public Settlement addSettlement(Settlement settlement) {
		return settlementDao.create(settlement);
	}

	@Transactional
	public Settlement modifySettlement(Settlement settlement) {
		return settlementDao.update(settlement);
	}

	@Transactional
	public void deleteSettlement(Long id) {
		settlementDao.delete(id);
	}
	
	@Transactional
	public void deleteSettlements(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					settlementDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<Settlement> settlements(int page, int limit) {
		return settlementDao.page(page, limit);
	}
	
	public List<Settlement> list() {
		return settlementDao.list();
	}

}
