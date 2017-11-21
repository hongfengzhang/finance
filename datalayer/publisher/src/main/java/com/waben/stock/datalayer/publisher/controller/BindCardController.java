package com.waben.stock.datalayer.publisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.BindCardService;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.BindCardInterface;

/**
 * 绑卡 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/bindCard")
public class BindCardController implements BindCardInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BindCardService bindCardService;

	@Override
	public Response<BindCardDto> bindBankCard(@RequestBody BindCardDto bindCardDto) {
		return new Response<>(bindCardService.bindBankCard(bindCardDto.getPublisherSerialCode(), bindCardDto.getName(),
				bindCardDto.getIdCard(), bindCardDto.getPhone(), bindCardDto.getBankCard()));
	}

	@Override
	public Response<List<BindCardDto>> publisherBankCardList(String serialCode) {
		return new Response<>(bindCardService.publisherBankCardList(serialCode));
	}

}
