package com.waben.stock.datalayer.stockoption.business;

import com.waben.stock.datalayer.stockoption.reference.CapitalAccountReference;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CapitalAccountBusiness {

	@Autowired
	@Qualifier("capitalAccountFeignReference")
	private CapitalAccountReference service;

	public CapitalAccountDto fetchByPublisherId(Long publisherId) {
		Response<CapitalAccountDto> response = service.fetchByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
}
