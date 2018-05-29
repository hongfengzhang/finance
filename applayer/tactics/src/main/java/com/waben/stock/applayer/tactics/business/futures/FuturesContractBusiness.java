package com.waben.stock.applayer.tactics.business.futures;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.service.futures.FuturesContractInterface;
import com.waben.stock.interfaces.service.futures.FuturesOrderInterface;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

@Service
public class FuturesContractBusiness {

	@Autowired
	@Qualifier("capitalAccountInterface")
	private CapitalAccountInterface service;

	@Autowired
	@Qualifier("futurescontractInterface")
	private FuturesContractInterface futuresContractInterface;

	@Autowired
	@Qualifier("futuresOrderInterface")
	private FuturesOrderInterface futuresOrderInterface;

	public CapitalAccountDto findByPublisherId(Long publisherId) {
		Response<CapitalAccountDto> response = service.fetchByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<FuturesContractDto> pagesContract(FuturesContractQuery query) throws Throwable {
		Response<PageInfo<FuturesContractDto>> response = futuresContractInterface.pagesContract(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesContractDto getContractByOne(FuturesContractQuery query) throws Throwable {
		Response<PageInfo<FuturesContractDto>> response = futuresContractInterface.pagesContract(query);
		if ("200".equals(response.getCode())) {
			FuturesContractDto contractDto = response.getResult().getContent().get(0);
			if (contractDto == null) {
				// 该合约不存在
				throw new ServiceException(ExceptionConstant.BUYRECORD_NONTRADINGPERIOD_EXCEPTION);
			}
			if (!contractDto.getEnable() || !contractDto.getChangeEnable()) {
				// 该合约异常不可用
				throw new ServiceException(ExceptionConstant.BUYRECORD_NONTRADINGPERIOD_EXCEPTION);
			}
			// 判断该合约是否处于交易中
			if (contractDto.getState() != 1) {
				// 该合约不在交易中
				throw new ServiceException(ExceptionConstant.BUYRECORD_NONTRADINGPERIOD_EXCEPTION);
			}
			return contractDto;
		}
		throw new ServiceException(response.getCode());
	}

	public BigDecimal sumUserNum(Long contractId, Long publisherId) {
		Response<BigDecimal> response = futuresOrderInterface.sumByListOrderContractIdAndPublisherId(contractId,
				publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesOrderDto buy(FuturesOrderDto orderDto) {
		Response<FuturesOrderDto> response = futuresOrderInterface.addOrder(orderDto);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
