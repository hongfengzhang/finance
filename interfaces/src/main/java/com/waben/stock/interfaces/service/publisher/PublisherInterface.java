package com.waben.stock.interfaces.service.publisher;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
public interface PublisherInterface {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)

	Response<PublisherDto> findById(@PathVariable("id") Long id);
	@RequestMapping(value = "/findByPhone", method = RequestMethod.GET)
	Response<PublisherDto> findByPhone(@RequestParam(name = "phone") String phone);

	@RequestMapping(value = "/findBySerialCode", method = RequestMethod.GET)
	Response<PublisherCapitalAccountDto> findBySerialCode(@RequestParam(name = "serialCode") String serialCode);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	Response<PublisherCapitalAccountDto> register(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password);

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.PUT)
	Response<PublisherCapitalAccountDto> modifyPassword(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password);

	@RequestMapping(value = "/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<String> modifyPaymentPassword(@RequestParam(name = "serialCode") String serialCode,
			@RequestParam(name = "paymentPassword") String paymentPassword);
	
	@RequestMapping(value = "/getCapitalAccount", method = RequestMethod.GET)
	Response<CapitalAccountDto> getCapitalAccount(@RequestParam(name = "serialCode") String serialCode);

}
