package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.BindingCard;
import com.waben.stock.datalayer.promotion.repository.BindingCardDao;

/**
 * 绑卡 Service
 * 
 * @author luomengan
 *
 */
@Service
public class BindingCardService {

	@Autowired
	private BindingCardDao bindingCardDao;

	public BindingCard getBindingCardInfo(Long id) {
		return bindingCardDao.retrieve(id);
	}

	@Transactional
	public BindingCard addBindingCard(BindingCard bindingCard) {
		return bindingCardDao.create(bindingCard);
	}

	@Transactional
	public BindingCard modifyBindingCard(BindingCard bindingCard) {
		return bindingCardDao.update(bindingCard);
	}

	@Transactional
	public void deleteBindingCard(Long id) {
		bindingCardDao.delete(id);
	}
	
	@Transactional
	public void deleteBindingCards(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					bindingCardDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<BindingCard> bindingCards(int page, int limit) {
		return bindingCardDao.page(page, limit);
	}
	
	public List<BindingCard> list() {
		return bindingCardDao.list();
	}

}
