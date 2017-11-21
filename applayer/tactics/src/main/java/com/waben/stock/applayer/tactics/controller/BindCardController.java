package com.waben.stock.applayer.tactics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.BindCardService;
import com.waben.stock.applayer.tactics.service.SmsCache;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.SmsType;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.ApiOperation;

/**
 * 绑卡 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/bindCard")
public class BindCardController {

	@Autowired
	private BindCardService bindCardService;

	@PostMapping("/bindBankCard")
	@ApiOperation(value = "绑定银行卡")
	public Response<BindCardDto> bindBankCard(@RequestParam(required = true) String name,
			@RequestParam(required = true) String idCard, @RequestParam(required = true) String phone,
			@RequestParam(required = true) String bankCard, @RequestParam(required = true) String verificationCode) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.BindCardCode, phone, verificationCode);
		// 绑定银行卡
		BindCardDto bindCardDto = new BindCardDto();
		bindCardDto.setBankCard(bankCard);
		bindCardDto.setIdCard(idCard);
		bindCardDto.setName(name);
		bindCardDto.setPhone(phone);
		bindCardDto.setPublisherSerialCode(SecurityUtil.getSerialCode());

		Response<BindCardDto> result = bindCardService.bindBankCard(bindCardDto);
		return result;
	}

	@GetMapping("/myBankCardList")
	@ApiOperation(value = "我的已绑定银行卡列表")
	public Response<List<BindCardDto>> myBankCardList() {
		return bindCardService.publisherBankCardList(SecurityUtil.getSerialCode());
	}

}