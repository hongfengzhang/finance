package com.waben.stock.datalayer.buyrecord.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.reference.CapitalAccountReference;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Service
public class CapitalAccountBusiness {

	@Autowired
	@Qualifier("capitalAccountFeignReference")
	private CapitalAccountReference service;

	public CapitalAccountDto serviceFeeAndReserveFund(Long publisherId, Long buyRecordId, BigDecimal serviceFee,
			BigDecimal reserveFund) {
		Response<CapitalAccountDto> response = service.serviceFeeAndReserveFund(publisherId, buyRecordId, serviceFee,
				reserveFund);
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

	public CapitalAccountDto deferredCharges(Long publisherId, Long buyRecordId, BigDecimal deferredCharges) {
		Response<CapitalAccountDto> response = service.deferredCharges(publisherId, buyRecordId, deferredCharges);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto returnReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal profitOrLoss) {
		Response<CapitalAccountDto> response = service.returnReserveFund(publisherId, buyRecordId, buyRecordSerialCode,
				profitOrLoss);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto revoke(Long publisherId, Long buyRecordId, BigDecimal serviceFee) {
		Response<CapitalAccountDto> response = service.revoke(publisherId, buyRecordId, serviceFee);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
