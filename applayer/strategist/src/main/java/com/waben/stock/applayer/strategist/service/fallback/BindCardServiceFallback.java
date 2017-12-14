package com.waben.stock.applayer.strategist.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.service.BindCardService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class BindCardServiceFallback implements BindCardService {

	@Override
	public Response<BindCardDto> addBankCard(BindCardDto bindCardDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<BindCardDto>> listsByPublisherId(Long publisherId) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> fetchById(Long id) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> modifyBankCard(BindCardDto bindCardDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
