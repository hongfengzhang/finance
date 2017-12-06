package com.waben.stock.datalayer.publisher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.BindCard;
import com.waben.stock.datalayer.publisher.repository.BindCardDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;

/**
 * 绑卡 Service
 * 
 * @author luomengan
 *
 */
@Service
public class BindCardService {

	@Autowired
	private BindCardDao bindCardDao;

	public BindCard save(Long publisherId, String name, String idCard, String phone, String bankCard) {
		BindCard bindCard = bindCardDao.retriveByPublisherIdAndBankCard(publisherId, bankCard);
		if (bindCard != null) {
			throw new ServiceException(ExceptionConstant.BANKCARD_ALREADY_BIND_EXCEPTION);
		} else {
			bindCard = new BindCard();
			bindCard.setName(name);
			bindCard.setIdCard(idCard);
			bindCard.setPhone(phone);
			bindCard.setPublisherId(publisherId);
			bindCard.setBankCard(bankCard);
			bindCardDao.create(bindCard);
		}
		return bindCard;
	}

	public List<BindCard> list(Long publisherId) {
		return bindCardDao.listByPublisherId(publisherId);
	}

	public BindCard findById(Long id) {
		return bindCardDao.retrieve(id);
	}

	public BindCard revision(BindCard bindCard) {
		return bindCardDao.update(bindCard);
	}

}
