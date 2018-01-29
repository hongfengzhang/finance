package com.waben.stock.applayer.strategist.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.strategist.reference.BindCardReference;
import com.waben.stock.interfaces.dto.manage.BankInfoDto;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
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

	public static Map<String, String> bankIconMap = new HashMap<>();

	@Autowired
	@Qualifier("bindCardReference")
	private BindCardReference bindCardReference;

	@Autowired
	private CnapsBusiness cnapsBusiness;

	@PostConstruct
	public void init() {
		try {
			List<BankInfoDto> list = cnapsBusiness.listBankInfo();
			for (BankInfoDto bankInfo : list) {
				bankIconMap.put(bankInfo.getBankName(), bankInfo.getIconLink());
			}
		} catch (Exception ex) {
			logger.error("缓存银行信息发生异常! {}", ex.getMessage());
		}
	}

	public BindCardDto findById(Long id) {
		Response<BindCardDto> response = bindCardReference.fetchById(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BindCardDto save(BindCardDto bindCard) {
		if (bankIconMap.size() == 0) {
			init();
		}
		BankInfoDto bankInfoDto = null;
		try {
			bankInfoDto = cnapsBusiness.findBankInfo(bindCard.getBankCard());
			if (bankInfoDto == null) {
				logger.error("未识别的银行卡号:{}", bindCard.getBankCard());
			}
		} catch (Exception ex) {
			logger.error("未识别的银行卡号:{}", bindCard.getBankCard());
		}
		bindCard.setBankName(bankInfoDto != null ? bankInfoDto.getBankName() : null);
		Response<BindCardDto> response = bindCardReference.addBankCard(bindCard);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
	public Long remove(Long id) {
		Response<Long> response = bindCardReference.dropBankCard(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BindCardDto revision(BindCardDto bindCard) {
		Response<BindCardDto> response = bindCardReference.modifyBankCard(bindCard);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<BindCardDto> listsByPublisherId(Long publisherId) {
		if (bankIconMap.size() == 0) {
			init();
		}
		Response<List<BindCardDto>> response = bindCardReference.listsByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
