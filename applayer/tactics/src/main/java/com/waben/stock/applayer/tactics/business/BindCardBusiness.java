package com.waben.stock.applayer.tactics.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.service.BindCardService;
import com.waben.stock.interfaces.dto.manage.BankInfoDto;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.BankType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 绑卡 Business
 * 
 * @author luomengan
 *
 */
@Service
public class BindCardBusiness {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BindCardService service;

	private CnapsBusiness cnapsBusiness;

	public BindCardDto findById(Long id) {
		Response<BindCardDto> response = service.fetchById(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BindCardDto save(BindCardDto bindCard) {
		BankType bankType = BankType.DEFAULT;
		try {
			BankInfoDto bankInfoDto = cnapsBusiness.findBankInfo(bindCard.getBankCard());
			if (bankInfoDto != null) {
				bankType = BankType.getByCode(bankInfoDto.getBankCode());
			}
		} catch (Exception ex) {
			logger.error("未识别的银行卡号:{}", bindCard.getBankCard());
		}
		bindCard.setBankName(bankType.getBank());
		Response<BindCardDto> response = service.addBankCard(bindCard);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BindCardDto revision(BindCardDto bindCard) {
		Response<BindCardDto> response = service.modifyBankCard(bindCard);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<BindCardDto> listsByPublisherId(Long publisherId) {
		Response<List<BindCardDto>> response = service.listsByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
