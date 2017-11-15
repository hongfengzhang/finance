package com.waben.stock.applayer.tactics.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.BindCardDto;
import com.waben.stock.interfaces.dto.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;

/**
 * @author Created by yuyidi on 2017/11/4.
 * @desc
 */
@Component
public class PublisherServiceFallback implements PublisherService {

	@Override
	public PublisherDto findById(Long id) {
		return new PublisherDto();
	}

	@Override
	public PublisherCapitalAccountDto register(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public PublisherCapitalAccountDto getCurrent(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public PublisherCapitalAccountDto modifyPassword(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public PublisherDto findByPhone(String phone) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public void modifyPaymentPassword(String serialCode, String paymentPassword) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public BindCardDto bindBankCard(String serialCode, String name, String idCard, String phone, String bankCard) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public List<BindCardDto> publisherBankCardList(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
