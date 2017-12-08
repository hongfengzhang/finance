package com.waben.stock.applayer.tactics.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.dto.publisher.PublisherCapitalAccountDto;
import com.waben.stock.applayer.tactics.dto.publisher.PublisherExtensionDto;
import com.waben.stock.applayer.tactics.dto.publisher.PublisherExtensionDto.PublisherExtensionUserDto;
import com.waben.stock.applayer.tactics.dto.publisher.SettingRemindDto;
import com.waben.stock.applayer.tactics.security.CustomUserDetails;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.security.jwt.JWTTokenUtil;
import com.waben.stock.applayer.tactics.service.BindCardService;
import com.waben.stock.applayer.tactics.service.CapitalAccountService;
import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.applayer.tactics.service.SmsCache;
import com.waben.stock.applayer.tactics.service.SmsService;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.SmsType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.RandomUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@RestController
@RequestMapping("/publisher")
@Api(description = "策略发布人")
public class PublisherController {

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private CapitalAccountService accountService;

	@Autowired
	private BindCardService bindCardService;

	@Autowired
	private SmsService smsService;

	@GetMapping("/{id}")
	public Response<PublisherDto> echo(@PathVariable Long id) {
		return publisherService.fetchById(id);
	}

	@PostMapping("/sendSms")
	@ApiOperation(value = "发送短信", notes = "type(1:注册,2:修改密码,3:绑定银行卡)")
	public Response<String> sendAuthCode(String phone, int type) {
		SmsType smsType = SmsType.getByIndex(String.valueOf(type));
		List<String> paramValues = new ArrayList<>();
		// 发送注册短信
		if (smsType == SmsType.RegistVerificationCode || smsType == SmsType.ModifyPasswordCode
				|| smsType == SmsType.BindCardCode) {
			paramValues.add(RandomUtil.generateRandomCode(4));
			paramValues.add("020-888888");
		}
		smsService.sendMessage(smsType, phone, paramValues);
		return new Response<>("发送成功");
	}

	@PostMapping("/register")
	@ApiOperation(value = "注册发布策略人")
	public Response<PublisherCapitalAccountDto> register(@RequestParam(required = true) String phone,
			@RequestParam(required = true) String password, @RequestParam(required = true) String verificationCode,
			String promoter) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.RegistVerificationCode, phone, verificationCode);
		// 注册
		Response<PublisherDto> publisherResp = publisherService.register(phone, password, promoter);
		Response<CapitalAccountDto> accountResp = accountService.fetchByPublisherId(publisherResp.getResult().getId());
		PublisherCapitalAccountDto data = new PublisherCapitalAccountDto(publisherResp.getResult(),
				accountResp.getResult());
		if ("200".equals(publisherResp.getCode()) && publisherResp.getResult() != null) {
			String token = JWTTokenUtil.generateToken(
					new CustomUserDetails(publisherResp.getResult().getId(), publisherResp.getResult().getSerialCode(),
							publisherResp.getResult().getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
			data.setToken(token);
		}
		return new Response<>(data);
	}

	@GetMapping("/getCurrent")
	@ApiOperation(value = "获取当前发布策略人信息")
	public Response<PublisherCapitalAccountDto> getCurrent() {
		Response<PublisherDto> publisherResp = publisherService.fetchById(SecurityUtil.getUserId());
		Response<CapitalAccountDto> accountResp = accountService.fetchByPublisherId(SecurityUtil.getUserId());
		PublisherCapitalAccountDto data = new PublisherCapitalAccountDto(publisherResp.getResult(),
				accountResp.getResult());
		return new Response<>(data);
	}

	@GetMapping("/getSettingRemind")
	@ApiOperation(value = "获取当前发布策略人设置提醒信息")
	public Response<SettingRemindDto> getSettingRemind() {
		Response<SettingRemindDto> result = new Response<>(new SettingRemindDto());
		result.getResult().setPhone(SecurityUtil.getUsername());
		// 获取是否绑卡
		Response<List<BindCardDto>> bindCardListResp = bindCardService.listsByPublisherId(SecurityUtil.getUserId());
		if ("200".equals(bindCardListResp.getCode())) {
			if (bindCardListResp.getResult() != null && bindCardListResp.getResult().size() > 0) {
				result.getResult().setSettingBindCard(true);
			}
		} else {
			result.setCode(bindCardListResp.getCode());
			result.setMessage(bindCardListResp.getMessage());
			return result;
		}
		// 获取是否设置过支付密码
		Response<CapitalAccountDto> capitalAccountResp = accountService.fetchByPublisherId(SecurityUtil.getUserId());
		if ("200".equals(capitalAccountResp.getCode())) {
			if (capitalAccountResp.getResult() != null && capitalAccountResp.getResult().getPaymentPassword() != null
					&& !"".equals(capitalAccountResp.getResult().getPaymentPassword())) {
				result.getResult().setSettingPaymentPassword(true);
			}
		} else {
			result.setCode(capitalAccountResp.getCode());
			result.setMessage(capitalAccountResp.getMessage());
			return result;
		}
		return result;
	}

	@PostMapping("/modifyPassword")
	@ApiOperation(value = "修改密码")
	public Response<PublisherCapitalAccountDto> modifyPassword(String phone, String password, String verificationCode) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.ModifyPasswordCode, phone, verificationCode);
		// 修改密码
		Response<PublisherDto> publisherResp = publisherService.modifyPassword(phone, password);
		Response<CapitalAccountDto> accountResp = accountService.fetchByPublisherId(publisherResp.getResult().getId());
		PublisherCapitalAccountDto data = new PublisherCapitalAccountDto(publisherResp.getResult(),
				accountResp.getResult());
		if ("200".equals(publisherResp.getCode()) && publisherResp.getResult() != null) {
			String token = JWTTokenUtil.generateToken(
					new CustomUserDetails(publisherResp.getResult().getId(), publisherResp.getResult().getSerialCode(),
							publisherResp.getResult().getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
			data.setToken(token);
		}
		return new Response<>(data);
	}

	@PostMapping("/modifyPaymentPassword")
	@ApiOperation(value = "设置支付密码")
	public Response<String> modifyPaymentPassword(String paymentPassword) {
		accountService.modifyPaymentPassword(SecurityUtil.getUserId(), paymentPassword);
		return new Response<>("设置支付密码成功");
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
