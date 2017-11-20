package com.waben.stock.applayer.tactics.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.security.CustomUserDetails;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.security.jwt.JWTTokenUtil;
import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.applayer.tactics.service.SmsCache;
import com.waben.stock.applayer.tactics.service.SmsService;
import com.waben.stock.interfaces.dto.BindCardDto;
import com.waben.stock.interfaces.dto.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.PublisherDto;
import com.waben.stock.interfaces.dto.PublisherExtensionDto;
import com.waben.stock.interfaces.dto.PublisherExtensionDto.PublisherExtensionUserDto;
import com.waben.stock.interfaces.enums.SmsType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.RandomUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@RestController
@RequestMapping("/publisher")
public class PublisherController {

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private SmsService smsService;

	@GetMapping("/{id}")
	public Response<PublisherDto> echo(@PathVariable Long id) {
		return publisherService.findById(id);
	}

	@PostMapping("/sendSms")
	@ApiOperation(value = "发送短信", notes = "type(1:注册,2:修改密码)")
	public Response<String> sendAuthCode(String phone, int type) {
		SmsType smsType = SmsType.getByIndex(String.valueOf(type));
		List<String> paramValues = new ArrayList<>();
		// 发送注册短信
		if (smsType == SmsType.RegistVerificationCode || smsType == SmsType.ModifyPasswordCode) {
			paramValues.add(RandomUtil.generateRandomCode(4));
			paramValues.add("020-888888");
		}
		smsService.sendMessage(smsType, phone, paramValues);
		return new Response<>("发送成功");
	}

	@PostMapping("/register")
	@ApiOperation(value = "注册发布策略人")
	public Response<PublisherCapitalAccountDto> register(String phone, String password, String verificationCode) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.RegistVerificationCode, phone, verificationCode);
		// 注册
		Response<PublisherCapitalAccountDto> result = publisherService.register(phone, password);
		if ("200".equals(result.getCode()) && result.getResult() != null) {
			String token = JWTTokenUtil
					.generateToken(new CustomUserDetails(result.getResult().getId(), result.getResult().getSerialCode(),
							result.getResult().getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
			result.getResult().setToken(token);
		}
		return result;
	}

	@GetMapping("/getCurrent")
	@ApiOperation(value = "获取当前发布策略人信息")
	public Response<PublisherCapitalAccountDto> getCurrent() {
		return publisherService.getCurrent(SecurityUtil.getSerialCode());
	}

	@PostMapping("/modifyPassword")
	@ApiOperation(value = "修改密码")
	public Response<PublisherCapitalAccountDto> modifyPassword(String phone, String password, String verificationCode) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.ModifyPasswordCode, phone, verificationCode);
		// 修改密码
		Response<PublisherCapitalAccountDto> result = publisherService.modifyPassword(phone, password);
		if ("200".equals(result.getCode()) && result.getResult() != null) {
			String token = JWTTokenUtil
					.generateToken(new CustomUserDetails(result.getResult().getId(), result.getResult().getSerialCode(),
							result.getResult().getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
			result.getResult().setToken(token);
		}
		return result;
	}

	@PutMapping("/modifyPaymentPassword")
	@ApiOperation(value = "设置支付密码")
	public Response<String> modifyPaymentPassword(String paymentPassword) {
		publisherService.modifyPaymentPassword(SecurityUtil.getSerialCode(), paymentPassword);
		return new Response<>("设置支付密码成功");
	}

	@PostMapping("/bindBankCard")
	@ApiOperation(value = "绑定银行卡")
	public Response<BindCardDto> bindBankCard(String name, String idCard, String phone, String bankCard) {
		return publisherService.bindBankCard(SecurityUtil.getSerialCode(), name, idCard, phone, bankCard);
	}

	@GetMapping("/myBankCardList")
	@ApiOperation(value = "我的已绑定银行卡列表")
	public Response<List<BindCardDto>> myBankCardList() {
		return publisherService.publisherBankCardList(SecurityUtil.getSerialCode());
	}

	@SuppressWarnings("unused")
	@GetMapping("/myExtension")
	@ApiOperation(value = "我的推广")
	public Response<PublisherExtensionDto> myExtension() {
		String serialCode = SecurityUtil.getSerialCode();
		// TODO 获取当前用户的推广详情，此处先模拟假数据
		PublisherExtensionDto result = new PublisherExtensionDto();
		result.setExtensionLink("http://www.baidu.com");
		result.setExtensionTotalProfit(new BigDecimal(100));
		result.setExtensionUserCount(5);
		List<PublisherExtensionUserDto> extensionUserList = new ArrayList<>();
		extensionUserList.add(new PublisherExtensionUserDto("12345678911", new BigDecimal(20), new Date()));
		extensionUserList.add(new PublisherExtensionUserDto("12345678912", new BigDecimal(20), new Date()));
		extensionUserList.add(new PublisherExtensionUserDto("12345678913", new BigDecimal(20), new Date()));
		extensionUserList.add(new PublisherExtensionUserDto("12345678914", new BigDecimal(20), new Date()));
		extensionUserList.add(new PublisherExtensionUserDto("12345678915", new BigDecimal(20), new Date()));
		result.setExtensionUserList(extensionUserList);
		return new Response<>(result);
	}

}
