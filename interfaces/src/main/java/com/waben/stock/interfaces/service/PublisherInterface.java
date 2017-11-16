package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.BindCardDto;
import com.waben.stock.interfaces.dto.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.PublisherDto;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
public interface PublisherInterface {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	PublisherDto findById(@PathVariable("id") Long id);

	@RequestMapping(value = "/findByPhone", method = RequestMethod.GET)
	PublisherDto findByPhone(String phone);

	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
	PublisherCapitalAccountDto getCurrent(String serialCode);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	PublisherCapitalAccountDto register(String phone, String password);

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.PUT)
	PublisherCapitalAccountDto modifyPassword(String phone, String password);

	@RequestMapping(value = "/modifyPaymentPassword", method = RequestMethod.PUT)
	void modifyPaymentPassword(String serialCode, String paymentPassword);

	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
	BindCardDto bindBankCard(String serialCode, String name, String idCard, String phone, String bankCard);

	@RequestMapping(value = "/publisherBankCardList", method = RequestMethod.GET)
	List<BindCardDto> publisherBankCardList(String serialCode);

}
