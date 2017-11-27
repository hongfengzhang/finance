package com.waben.stock.interfaces.service.publisher;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.pojo.Response;

public interface CapitalAccountInterface {

	@RequestMapping(value = "/publisherSerialCode/{serialCode}", method = RequestMethod.GET)
	Response<CapitalAccountDto> fetchByPublisherSerialCode(@PathVariable(name = "serialCode") String serialCode);

	@RequestMapping(value = "/publisherId/{publisherId}", method = RequestMethod.GET)
	Response<CapitalAccountDto> fetchByPublisherId(@PathVariable(name = "publisherId") Long publisherId);

	@RequestMapping(value = "/{publisherId}/recharge/{amount}", method = RequestMethod.POST)
	Response<CapitalAccountDto> recharge(@PathVariable(name = "publisherId") Long publisherId,
			@PathVariable(name = "amount") BigDecimal amount);

	@RequestMapping(value = "/{publisherId}/withdrawals/{amount}", method = RequestMethod.POST)
	Response<CapitalAccountDto> withdrawals(@PathVariable(name = "publisherId") Long publisherId,
			@PathVariable(name = "amount") BigDecimal amount);

	@RequestMapping(value = "/{publisherId}/{buyRecordId}/serviceFee/{serviceFee}/reserveFund/{reserveFund}", method = RequestMethod.POST)
	Response<CapitalAccountDto> serviceFeeAndReserveFund(@PathVariable(name = "publisherId") Long publisherId,
			@PathVariable(name = "buyRecordId") Long buyRecordId,
			@RequestParam(name = "buyRecordSerialCode") String buyRecordSerialCode,
			@PathVariable(name = "serviceFee") BigDecimal serviceFee,
			@PathVariable(name = "reserveFund") BigDecimal reserveFund);

	@RequestMapping(value = "/{publisherId}/deferredCharges/{deferredCharges}", method = RequestMethod.POST)
	Response<CapitalAccountDto> deferredCharges(@PathVariable(name = "publisherId") Long publisherId,
			@PathVariable(name = "deferredCharges") BigDecimal deferredCharges);

	@RequestMapping(value = "/{publisherId}/{buyRecordId}/returnCompensate/{profitOrLoss}", method = RequestMethod.POST)
	Response<CapitalAccountDto> returnReserveFund(@PathVariable(name = "publisherId") Long publisherId,
			@PathVariable(name = "buyRecordId") Long buyRecordId,
			@RequestParam(name = "buyRecordSerialCode") String buyRecordSerialCode,
			@PathVariable(name = "profitOrLoss") BigDecimal profitOrLoss);

	@RequestMapping(value = "/{publisherId}/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<String> modifyPaymentPassword(@PathVariable(name = "publisherId") Long publisherId,
			@RequestParam(name = "paymentPassword") String paymentPassword);

}