package com.waben.stock.datalayer.publisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.PublisherService;
import com.waben.stock.interfaces.dto.BindCardDto;
import com.waben.stock.interfaces.dto.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.PublisherDto;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;

/**
 * @author Created by yuyidi on 2017/11/5.
 * @desc
 */
@RestController
@RequestMapping("/publisher")
public class PublisherController implements PublisherInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PublisherService publisherService;

	@Override
	public PublisherDto findById(@PathVariable Long id) {
		logger.info("获取发布策略人信息:{}", id);
		return publisherService.findById(id);
	}

	@Override
	public PublisherCapitalAccountDto register(String phone, String password) {
		return publisherService.register(phone, password);
	}

	@Override
	public PublisherCapitalAccountDto getCurrent(String serialCode) {
		return publisherService.findBySerialCode(serialCode);
	}

	@Override
	public PublisherCapitalAccountDto modifyPassword(String phone, String password) {
		return publisherService.modifyPassword(phone, password);
	}

	@Override
	public PublisherDto findByPhone(String phone) {
		return publisherService.findByPhone(phone);
	}

	@Override
	public void modifyPaymentPassword(String serialCode, String paymentPassword) {
		publisherService.modifyPaymentPassword(serialCode, paymentPassword);
	}

	@Override
	public BindCardDto bindBankCard(String serialCode, String name, String idCard, String phone, String bankCard) {
		return publisherService.bindBankCard(serialCode, name, idCard, phone, bankCard);
	}

	@Override
	public List<BindCardDto> publisherBankCardList(String serialCode) {
		return publisherService.publisherBankCardList(serialCode);
	}

}
