package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.BindCardDto;
import com.waben.stock.interfaces.dto.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.PublisherDto;
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

	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
	Response<PublisherCapitalAccountDto> getCurrent(@RequestParam(name = "serialCode") String serialCode);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	Response<PublisherCapitalAccountDto> register(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password);

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.PUT)
	Response<PublisherCapitalAccountDto> modifyPassword(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password);

	@RequestMapping(value = "/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<String> modifyPaymentPassword(@RequestParam(name = "serialCode") String serialCode,
			@RequestParam(name = "paymentPassword") String paymentPassword);

	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
	Response<BindCardDto> bindBankCard(@RequestParam(name = "serialCode") String serialCode,
			@RequestParam(name = "name") String name, @RequestParam(name = "idCard") String idCard,
			@RequestParam(name = "phone") String phone, @RequestParam(name = "bankCard") String bankCard);

	@RequestMapping(value = "/publisherBankCardList", method = RequestMethod.GET)
	Response<List<BindCardDto>> publisherBankCardList(@RequestParam(name = "serialCode") String serialCode);

}
