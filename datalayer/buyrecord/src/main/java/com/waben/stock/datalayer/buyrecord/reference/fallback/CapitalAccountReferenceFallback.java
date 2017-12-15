package com.waben.stock.datalayer.buyrecord.reference.fallback;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

@Component
public class CapitalAccountReferenceFallback implements CapitalAccountInterface {

	@Override
	public Response<PageInfo<CapitalAccountDto>> pages(CapitalAccountQuery publisherQuery) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> fetchByPublisherSerialCode(String serialCode) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> fetchByPublisherId(Long publisherId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> recharge(Long publisherId, BigDecimal amount) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> withdrawals(Long publisherId, BigDecimal amount) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> serviceFeeAndReserveFund(Long publisherId, Long buyRecordId,
			String buyRecordSerialCode, BigDecimal serviceFee, BigDecimal reserveFund) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<FrozenCapitalDto> fetchFrozenCapital(Long publisherId, Long buyRecordId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> deferredCharges(Long publisherId, Long buyRecordId, BigDecimal deferredCharges) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> returnReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal profitOrLoss) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> modifyPaymentPassword(Long publisherId, String paymentPassword) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
