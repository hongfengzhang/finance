package com.waben.stock.interfaces.service.publisher;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.pojo.Response;

public interface CapitalAccountInterface {

	@RequestMapping(value = "/findByPublisherSerialCode", method = RequestMethod.GET)
	Response<CapitalAccountDto> findByPublisherSerialCode(
			@RequestParam(name = "publisherSerialCode") String publisherSerialCode);

	@RequestMapping(value = "/findByPublisherId", method = RequestMethod.GET)
	Response<CapitalAccountDto> findByPublisherId(@RequestParam(name = "publisherId") Long publisherId);

	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	Response<CapitalAccountDto> recharge(@RequestParam(name = "publisherId") Long publisherId,
			@RequestParam(name = "publisherSerialCode") String publisherSerialCode,
			@RequestParam(name = "amount") BigDecimal amount);

	@RequestMapping(value = "/withdrawals", method = RequestMethod.POST)
	Response<CapitalAccountDto> withdrawals(@RequestParam(name = "publisherId") Long publisherId,
			@RequestParam(name = "publisherSerialCode") String publisherSerialCode,
			@RequestParam(name = "amount") BigDecimal amount);

	@RequestMapping(value = "/serviceFeeAndCompensateMoney", method = RequestMethod.POST)
	Response<CapitalAccountDto> serviceFeeAndCompensateMoney(@RequestParam(name = "publisherId") Long publisherId,
			@RequestParam(name = "publisherSerialCode") String publisherSerialCode,
			@RequestParam(name = "buyRecordId") Long buyRecordId,
			@RequestParam(name = "buyRecordSerialCode") String buyRecordSerialCode,
			@RequestParam(name = "serviceFeeAmount") BigDecimal serviceFeeAmount,
			@RequestParam(name = "compensateMoneyAmount") BigDecimal compensateMoneyAmount);

	@RequestMapping(value = "/deferredCharges", method = RequestMethod.POST)
	Response<CapitalAccountDto> deferredCharges(@RequestParam(name = "publisherId") Long publisherId,
			@RequestParam(name = "publisherSerialCode") String publisherSerialCode,
			@RequestParam(name = "amount") BigDecimal amount);

	@RequestMapping(value = "/returnCompensateAndLoss", method = RequestMethod.POST)
	Response<CapitalAccountDto> returnCompensateAndLoss(@RequestParam(name = "publisherId") Long publisherId,
			@RequestParam(name = "publisherSerialCode") String publisherSerialCode,
			@RequestParam(name = "buyRecordId") Long buyRecordId,
			@RequestParam(name = "buyRecordSerialCode") String buyRecordSerialCode,
			@RequestParam(name = "profitOrLoss") BigDecimal profitOrLoss);

}