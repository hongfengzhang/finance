package com.waben.stock.futuresgateway.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.futuresgateway.dao.BindCardDao;
import com.waben.stock.futuresgateway.entity.BindCard;

/**
 * 绑卡 Service
 * 
 * @author luomengan
 *
 */
@Service
public class BindCardService {

	@Autowired
	private BindCardDao contractDao;

	public BindCard getBindCardInfo(Long id) {
		return contractDao.retrieveBindCardById(id);
	}

	@Transactional
	public BindCard addBindCard(BindCard bindCard) {
		return contractDao.createBindCard(bindCard);
	}

	@Transactional
	public BindCard modifyBindCard(BindCard bindCard) {
		return contractDao.updateBindCard(bindCard);
	}

	@Transactional
	public void deleteBindCard(Long id) {
		contractDao.deleteBindCardById(id);
	}
	
	@Transactional
	public void deleteBindCards(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					contractDao.deleteBindCardById(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<BindCard> bindCards(int page, int limit) {
		return contractDao.pageBindCard(page, limit);
	}
	
	public List<BindCard> list() {
		return contractDao.listBindCard();
	}

}
