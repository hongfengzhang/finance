package com.waben.stock.datalayer.publisher.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.BindCard;
import com.waben.stock.datalayer.publisher.repository.BindCardDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
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

	public BindCardDto bindBankCard(String serialCode, String name, String idCard, String phone, String bankCard) {
		BindCard bindCard = bindCardDao.findByPublisherSerialCodeAndBankCard(serialCode, bankCard);
		if (bindCard != null) {
			throw new ServiceException(ExceptionConstant.BANKCARD_ALREADY_BIND_EXCEPTION);
		} else {
			bindCard = new BindCard();
			bindCard.setName(name);
			bindCard.setIdCard(idCard);
			bindCard.setPhone(phone);
			bindCard.setPublisherSerialCode(serialCode);
			bindCard.setBankCard(bankCard);
			bindCardDao.create(bindCard);
		}
		return bindCard.copy();
	}

	public List<BindCardDto> publisherBankCardList(String serialCode) {
		List<BindCardDto> result = new ArrayList<>();
		List<BindCard> bindCardList = bindCardDao.findByPublisherSerialCode(serialCode);
		if (bindCardList != null && bindCardList.size() > 0) {
			for (BindCard bindCard : bindCardList) {
				result.add(bindCard.copy());
			}
		}
		return result;
	}

}
