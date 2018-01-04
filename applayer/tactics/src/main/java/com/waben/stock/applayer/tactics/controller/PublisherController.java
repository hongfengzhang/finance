package com.waben.stock.applayer.tactics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.waben.stock.applayer.tactics.business.BindCardBusiness;
import com.waben.stock.applayer.tactics.business.CapitalAccountBusiness;
import com.waben.stock.applayer.tactics.business.PublisherBusiness;
import com.waben.stock.applayer.tactics.dto.publisher.PublisherCapitalAccountDto;
import com.waben.stock.applayer.tactics.dto.publisher.SettingRemindDto;
import com.waben.stock.applayer.tactics.security.CustomUserDetails;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.security.jwt.JWTTokenUtil;
import com.waben.stock.applayer.tactics.service.SmsCache;
import com.waben.stock.applayer.tactics.service.SmsService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.SmsType;
import com.waben.stock.interfaces.exception.ServiceException;
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
	private PublisherBusiness publisherBusiness;

	@Autowired
	private CapitalAccountBusiness accountBusiness;

	@Autowired
	private BindCardBusiness bindCardBusiness;

	@Autowired
	private SmsService smsService;

	@GetMapping("/{id}")
	public Response<PublisherDto> echo(@PathVariable Long id) {
		return new Response<>(publisherBusiness.findById(id));
	}

	@PostMapping("/sendSms")
	@ApiOperation(value = "发送短信", notes = "type(1:注册,2:修改密码,3:绑定银行卡,5:修改支付密码)")
	public Response<String> sendAuthCode(String phone, int type) {
		SmsType smsType = SmsType.getByIndex(String.valueOf(type));
		List<String> paramValues = new ArrayList<>();
		// 发送注册短信
		if (smsType == SmsType.RegistVerificationCode || smsType == SmsType.ModifyPasswordCode
				|| smsType == SmsType.BindCardCode || smsType == SmsType.ModifyPaymentPwdCode) {
			paramValues.add(RandomUtil.generateRandomCode(4));
		}
		smsService.sendMessage(smsType, phone, paramValues);
		return new Response<>();
	}

	@GetMapping("/paymentPwdCode/validation/{verificationCode}")
	@ApiOperation(value = "验证修改支付密码的验证码是否正确")
	public Response<Boolean> validatePaymentPwdCode(@PathVariable("verificationCode") String verificationCode) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.ModifyPaymentPwdCode, SecurityUtil.getUsername(), verificationCode);
		return new Response<>(true);
	}

	@PostMapping("/register")
	@ApiOperation(value = "注册发布策略人")
	public Response<PublisherCapitalAccountDto> register(@RequestParam(required = true) String phone,
			@RequestParam(required = true) String password, @RequestParam(required = true) String verificationCode,
			String promoter, HttpServletRequest request) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.RegistVerificationCode, phone, verificationCode);
		// 注册
		PublisherDto publisher = publisherBusiness.register(phone, password, promoter, request.getHeader("endType"));
		CapitalAccountDto account = accountBusiness.findByPublisherId(publisher.getId());
		PublisherCapitalAccountDto data = new PublisherCapitalAccountDto(publisher, account);
		String token = JWTTokenUtil.generateToken(new CustomUserDetails(publisher.getId(), publisher.getSerialCode(),
				publisher.getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
		data.setToken(token);
		return new Response<>(data);
	}

	@GetMapping("/getCurrent")
	@ApiOperation(value = "获取当前发布策略人信息")
	public Response<PublisherCapitalAccountDto> getCurrent() {
		PublisherDto publisher = publisherBusiness.findById(SecurityUtil.getUserId());
		CapitalAccountDto account = accountBusiness.findByPublisherId(SecurityUtil.getUserId());
		return new Response<>(new PublisherCapitalAccountDto(publisher, account));
	}

	@GetMapping("/getSettingRemind")
	@ApiOperation(value = "获取当前发布策略人设置提醒信息")
	public Response<SettingRemindDto> getSettingRemind() {
		Response<SettingRemindDto> result = new Response<>(new SettingRemindDto());
		result.getResult().setPhone(SecurityUtil.getUsername());
		// 获取是否绑卡
		List<BindCardDto> bindCardList = bindCardBusiness.listsByPublisherId(SecurityUtil.getUserId());
		if (bindCardList != null && bindCardList.size() > 0) {
			result.getResult().setSettingBindCard(true);
		}
		// 获取是否设置过支付密码
		CapitalAccountDto account = accountBusiness.findByPublisherId(SecurityUtil.getUserId());
		if (account != null && account.getPaymentPassword() != null && !"".equals(account.getPaymentPassword())) {
			result.getResult().setSettingPaymentPassword(true);
		}
		return result;
	}

	@PostMapping("/modifyPassword")
	@ApiOperation(value = "修改密码")
	public Response<PublisherCapitalAccountDto> modifyPassword(String phone, String password, String verificationCode) {
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.ModifyPasswordCode, phone, verificationCode);
		// 修改密码
		PublisherDto publisher = publisherBusiness.modifyPassword(phone, password);
		CapitalAccountDto account = accountBusiness.findByPublisherId(publisher.getId());
		PublisherCapitalAccountDto data = new PublisherCapitalAccountDto(publisher, account);
		String token = JWTTokenUtil.generateToken(new CustomUserDetails(publisher.getId(), publisher.getSerialCode(),
				publisher.getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
		data.setToken(token);
		return new Response<>(data);
	}

	@PostMapping("/resetPassword")
	@ApiOperation(value = "根据原密码重设密码")
	public Response<PublisherCapitalAccountDto> resetPassword(String oldPassword, String newPassword) {
		// 验证原始密码是否正确
		PublisherDto publisher = publisherBusiness.findById(SecurityUtil.getUserId());
		if (!oldPassword.equals(publisher.getPassword())) {
			throw new ServiceException(ExceptionConstant.ORIGINAL_PASSWORD_MISMATCH_EXCEPTION);
		}
		// 修改密码
		publisherBusiness.modifyPassword(publisher.getPhone(), newPassword);
		CapitalAccountDto account = accountBusiness.findByPublisherId(publisher.getId());
		PublisherCapitalAccountDto data = new PublisherCapitalAccountDto(publisher, account);
		String token = JWTTokenUtil.generateToken(new CustomUserDetails(publisher.getId(), publisher.getSerialCode(),
				publisher.getPhone(), null, JWTTokenUtil.getAppGrantedAuthList()));
		data.setToken(token);
		return new Response<>(data);
	}
	
	@PostMapping("/modifyPaymentPassword")
	@ApiOperation(value = "设置支付密码")
	public Response<String> modifyPaymentPassword(String phone, String paymentPassword, String verificationCode) {
		if (!SecurityUtil.getUsername().equals(phone)) {
			throw new ServiceException(ExceptionConstant.PHONE_MISMATCH_EXCEPTION);
		}
		// 检查验证码
		SmsCache.matchVerificationCode(SmsType.ModifyPaymentPwdCode, phone, verificationCode);
		accountBusiness.modifyPaymentPassword(SecurityUtil.getUserId(), paymentPassword);
		return new Response<>();
	}
	
	@ApiOperation(value = "上传用户头像")
	@PostMapping("/headPortrait")
	public Response<String> uploadHeadPortrait(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Response<String> response = new Response<String>();
		response.setResult(publisherBusiness.uploadHeadPortrait(SecurityUtil.getUserId(), file));
		return response;
	}

}
