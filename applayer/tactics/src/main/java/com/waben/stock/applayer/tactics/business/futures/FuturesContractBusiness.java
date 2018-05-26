package com.waben.stock.applayer.tactics.business.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.service.futures.FuturesContractInterface;

@Service
public class FuturesContractBusiness {

	@Autowired
	@Qualifier("futurescontractInterface")
	private FuturesContractInterface futuresContractInterface;

	public PageInfo<FuturesContractDto> pagesContract(FuturesContractQuery futuresContractQuery) {
		Response<PageInfo<FuturesContractDto>> response = futuresContractInterface.pagesContract(futuresContractQuery);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
