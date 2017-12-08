package com.waben.stock.interfaces.service.publisher;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.pojo.Response;

public interface CapitalAccountInterface {

	@RequestMapping(value = "/publisherSerialCode/{serialCode}", method = RequestMethod.GET)
	Response<CapitalAccountDto> fetchByPublisherSerialCode(@PathVariable("serialCode") String serialCode);

	@RequestMapping(value = "/publisherId/{publisherId}", method = RequestMethod.GET)
	Response<CapitalAccountDto> fetchByPublisherId(@PathVariable("publisherId") Long publisherId);

	@RequestMapping(value = "/{publisherId}/recharge/{amount}", method = RequestMethod.POST)
	Response<CapitalAccountDto> recharge(@PathVariable("publisherId") Long publisherId,
			@PathVariable("amount") BigDecimal amount);

	@RequestMapping(value = "/{publisherId}/withdrawals/{amount}", method = RequestMethod.POST)
	Response<CapitalAccountDto> withdrawals(@PathVariable("publisherId") Long publisherId,
			@PathVariable("amount") BigDecimal amount);

	@RequestMapping(value = "/{publisherId}/{buyRecordId}/serviceFee/{serviceFee}/reserveFund/{reserveFund}", method = RequestMethod.POST)
	Response<CapitalAccountDto> serviceFeeAndReserveFund(@PathVariable("publisherId") Long publisherId,
			@PathVariable("buyRecordId") Long buyRecordId,
			@RequestParam(name = "buyRecordSerialCode") String buyRecordSerialCode,
			@PathVariable("serviceFee") BigDecimal serviceFee, @PathVariable("reserveFund") BigDecimal reserveFund);

	@RequestMapping(value = "/frozenCapital/{publisherId}/{buyRecordId}/", method = RequestMethod.GET)
	Response<FrozenCapitalDto> fetchFrozenCapital(@PathVariable("publisherId") Long publisherId,
			@PathVariable("buyRecordId") Long buyRecordId);

	@RequestMapping(value = "/{publisherId}/{buyRecordId}/deferredCharges/{deferredCharges}", method = RequestMethod.POST)
	Response<CapitalAccountDto> deferredCharges(@PathVariable("publisherId") Long publisherId,
			@PathVariable("buyRecordId") Long buyRecordId, @PathVariable("deferredCharges") BigDecimal deferredCharges);

	@RequestMapping(value = "/{publisherId}/{buyRecordId}/returnCompensate/{profitOrLoss}", method = RequestMethod.POST)
	Response<CapitalAccountDto> returnReserveFund(@PathVariable("publisherId") Long publisherId,
			@PathVariable("buyRecordId") Long buyRecordId,
			@RequestParam(name = "buyRecordSerialCode") String buyRecordSerialCode,
			@PathVariable("profitOrLoss") BigDecimal profitOrLoss);

	@RequestMapping(value = "/{publisherId}/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<String> modifyPaymentPassword(@PathVariable("publisherId") Long publisherId,
			@RequestParam(name = "paymentPassword") String paymentPassword);

}