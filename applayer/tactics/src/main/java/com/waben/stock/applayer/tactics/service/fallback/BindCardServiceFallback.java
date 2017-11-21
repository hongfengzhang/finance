package com.waben.stock.applayer.tactics.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.service.BindCardService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class BindCardServiceFallback implements BindCardService {

	@Override
	public Response<BindCardDto> bindBankCard(BindCardDto bindCardDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<BindCardDto>> publisherBankCardList(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
