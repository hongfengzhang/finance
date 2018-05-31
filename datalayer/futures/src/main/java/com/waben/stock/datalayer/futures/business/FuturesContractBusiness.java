package com.waben.stock.datalayer.futures.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

/**
 * 期货合约 Business
 * 
 * @author sl
 *
 */
@Service
public class FuturesContractBusiness {

	@Autowired
	@Qualifier("capitalAccountInterface")
	private CapitalAccountInterface service;

	public CapitalAccountDto findByPublisherId(Long publisherId) {
		Response<CapitalAccountDto> response = service.fetchByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto serviceFeeAndReserveFund(Long publisherId, Long buyRecordId, BigDecimal serviceFee,
			BigDecimal reserveFund, BigDecimal deferredFee) {
		Response<CapitalAccountDto> response = service.serviceFeeAndReserveFund(publisherId, buyRecordId, serviceFee,
				reserveFund, deferredFee);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FrozenCapitalDto fetchFrozenCapital(Long publisherId, Long buyRecordId) {
		Response<FrozenCapitalDto> response = service.fetchFrozenCapital(publisherId, buyRecordId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto futuresOrderServiceFeeAndReserveFund(Long publisherId, Long orderId, BigDecimal serviceFee,
			BigDecimal reserveFund) {
		Response<CapitalAccountDto> response = service.futuresOrderServiceFeeAndReserveFund(publisherId, orderId,
				serviceFee, reserveFund);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FrozenCapitalDto futuresOrderFetchFrozenCapital(Long publisherId, Long orderId) {
		Response<FrozenCapitalDto> response = service.futuresOrderFetchFrozenCapital(publisherId, orderId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
}
