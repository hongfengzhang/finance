package com.waben.stock.applayer.tactics.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.BindCardDto;
import com.waben.stock.interfaces.dto.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * @author Created by yuyidi on 2017/11/4.
 * @desc
 */
@Component
public class PublisherServiceFallback implements PublisherService {

	@Override
	public Response<PublisherDto> findById(Long id) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherCapitalAccountDto> register(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherCapitalAccountDto> getCurrent(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherCapitalAccountDto> modifyPassword(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> findByPhone(String phone) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> modifyPaymentPassword(String serialCode, String paymentPassword) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> bindBankCard(String serialCode, String name, String idCard, String phone,
			String bankCard) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<BindCardDto>> publisherBankCardList(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
