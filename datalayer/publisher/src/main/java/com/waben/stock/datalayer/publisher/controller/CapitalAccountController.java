package com.waben.stock.datalayer.publisher.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.CapitalAccountService;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

/**
 * 资金账户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/capitalAccount")
public class CapitalAccountController implements CapitalAccountInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CapitalAccountService capitalAccountService;

	@Override
	public Response<CapitalAccountDto> findByPublisherSerialCode(String publisherSerialCode) {
		return new Response<>(capitalAccountService.findByPublisherSerialCode(publisherSerialCode));
	}

	@Override
	public Response<CapitalAccountDto> findByPublisherId(Long publisherId) {
		return new Response<>(capitalAccountService.findByPublisherId(publisherId));
	}

	@Override
	public Response<CapitalAccountDto> recharge(Long publisherId, String publisherSerialCode, BigDecimal amount) {
		return new Response<>(capitalAccountService.recharge(publisherId, publisherSerialCode, amount));
	}

	@Override
	public Response<CapitalAccountDto> withdrawals(Long publisherId, String publisherSerialCode, BigDecimal amount) {
		return new Response<>(capitalAccountService.withdrawals(publisherId, publisherSerialCode, amount));
	}

	@Override
	public Response<CapitalAccountDto> serviceFeeAndCompensateMoney(Long publisherId, String publisherSerialCode,
			Long buyRecordId, String buyRecordSerialCode, BigDecimal serviceFeeAmount,
			BigDecimal compensateMoneyAmount) {
		return new Response<>(capitalAccountService.serviceFeeAndCompensateMoney(publisherId, publisherSerialCode,
				buyRecordId, buyRecordSerialCode, serviceFeeAmount, compensateMoneyAmount));
	}

	@Override
	public Response<CapitalAccountDto> deferredCharges(Long publisherId, String publisherSerialCode,
			BigDecimal amount) {
		return new Response<>(capitalAccountService.deferredCharges(publisherId, publisherSerialCode, amount));
	}

	@Override
	public Response<CapitalAccountDto> returnCompensateAndLoss(Long publisherId, String publisherSerialCode,
			Long buyRecordId, String buyRecordSerialCode, BigDecimal profitOrLoss) {
		return new Response<>(capitalAccountService.returnCompensateAndLoss(publisherId, publisherSerialCode,
				buyRecordId, buyRecordSerialCode, profitOrLoss));
	}

}
