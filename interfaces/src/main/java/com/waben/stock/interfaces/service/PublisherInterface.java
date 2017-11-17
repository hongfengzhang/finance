package com.waben.stock.interfaces.service.publisher;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	Response<PublisherDto> findByPhone(String phone);

	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
	Response<PublisherCapitalAccountDto> getCurrent(String serialCode);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	Response<PublisherCapitalAccountDto> register(String phone, String password);

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.PUT)
	Response<PublisherCapitalAccountDto> modifyPassword(String phone, String password);

	@RequestMapping(value = "/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<String> modifyPaymentPassword(String serialCode, String paymentPassword);

	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
	Response<BindCardDto> bindBankCard(String serialCode, String name, String idCard, String phone, String bankCard);

	@RequestMapping(value = "/publisherBankCardList", method = RequestMethod.GET)
	Response<List<BindCardDto>> publisherBankCardList(String serialCode);

}
