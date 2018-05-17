package com.waben.stock.applayer.operation.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.operation.service.publisher.BindCardService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.BindCardResourceType;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Service
public class BindCardBusiness {

	@Autowired
	@Qualifier("bindCardFeignService")
	private BindCardService bindCardService;

	public List<BindCardDto> fetchBindCardByPublisherId(Long publisherId) {
		Response<List<BindCardDto>> response = bindCardService
				.listsByResourceTypeAndResourceId(BindCardResourceType.PUBLISHER.getIndex(), publisherId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

}
